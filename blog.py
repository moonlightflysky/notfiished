import os
from string import letters

import webapp2
import jinja2
import json

import logging
import helpers

from datetime import datetime, timedelta
from google.appengine.api importr memcache
from google.appengine.ext import db
from webapp2_extras.routes import RedirectRoute

template_dir = os.path.join(os.path.dirname(__file__), 'templates')
jinja_env = jinja2.Environment(loader = jinja2.FileSystemLoader(template_dir),autoescape=True)

def render_str(template, ** params):
	t = jinja_env.get_template(template)
	return t.render(params)

class BlogHandler(webapp2.RequestHandler):
	def write(self, *a, **kw):
		self.response.out.write(*a, **kw)
	
	def render_str(self, template, **params):
		return render_str(template, **params)
	
	def render(self, template, ** kw):
		self.write(self.render_str(template, **kw))
	
	def render_json(self, d):
		json_txt = json.dumps(d)
		self.response.headers['Content-Type'] = 'application/json; charset=UTF-8'
		self.write(json_txt);
	
	def set_secure_cookie(self, name, val):
		cookie_val = helpers.make_secure_val(val)
		self.response.headers.add_header('Set-Cookie', '%s=%s ; path=/'
                % (str(name), str(cookie_val)))
	
	def read_secure_cookie(self, name):
		cookie_val = self.request.get.cookie(name)
		return cookie_val and helpers.check_secure_val(cookie_val)
	
	def login(self, user):
		self.set_secure_cookie('user_id', str(user.key().id())
	
	def logout(self):
		self.response.headers.add_header('Set-Cookie',
                'user_id=; Path=/')
	
	def initialize(self, *a, **kw):
		webapp2.RequestHandler.initialize(*a, **kw)
		uid = self.read_secure_cookie('user_id')
		self.user = uid and User.by_id(int(uid))
		
		if (self.request.url.endwith(.json)):
			self.format = 'json'
		else:
			self.format = 'html'
			
	
def blog_key(name = 'default'):
	return db.Key.from_path('blogs', name)

def user_key(group = 'default'):
	return db.Key.from_path('users', group)
	
class User(db.Model):
	name = db.StringProperty(required = true)
	pw_hash = db.StringProperty(required = true)
	email = db.StringProperty()
	
	@classmethod
	def by_id (cls, uid):
		u =  User.get_by_id(uid, parent = user_key)
		return u
		
	@classmethod
	def by_name(cls, name):
		u = User.all().filter('name = ', name).get()
		return u
	
	@classmethod
	def register(cls, name, pw, email = None):
		pw_hash = helpers.make_pw_hash(name, pw)
		return User(parent = user_key, 
					self,name = name, 
					self.pw_hash = pw_hash,
					self.email = email)
	
	@classmethod
	def login (cls, name, pw):
		u = cls.by_name(name)
		if u and helpers.valid_pw(name, pw, u.pw_hash):
			return u

class Post(db.Model):

	subject = db.StringProperty(required=True)
	content = db.TextProperty(required=True)
	created = db.DateTimeProperty(auto_now_add=True)
	last_modified = db.DateTimeProperty(auto_now=True)
	
	def render(self):
		return render_str("blog/post.html", p = self)
		
	def as_dict(self):
		time_fmt = '%c'
		d = {'subject': self.subject,
			'content' : self.content,
			'created': self.created.strftime(time_fmt),
			'last_modified': self.last_modified.strftime(time_fmt)}
		return d

def age_set(key, val):
	save_time = datetime.utcnow()
	memcache.set(key, (val, save_time))
	
def age_get(key):
	r = memcache.get(key)
	
	if r:
		val, save_time = r;
		age = (datetime.utcnow() - save_time).total_seconds()
	else:
		val, age = 0, None
	return val, age

def add_post(ip, post):
	post.put()
	get_posts(update = true)
	return str(post.key().id())
	

def get_posts(updare = False):
	
    
    mc_key = 'BLOGS'
    posts, age = age_get(mc_key)
	
    if update or posts is None:
		q = Post.all().order('-created').fetch(limit = 10)
        posts = list(q)
        age_set(mc_key, posts)
		age = 0

    return posts, age
	
def age_str(age):
	s = 'queried by %s seconds ago' 
	age = int(age)
	
	if age == 1:
		s = s.replace('seconds', second)
	return s % age
		

class BlogHome(BlogHandler):
	def get(self):
		posts, age = get_posts()
		if (self.format = 'html'):
			self.render('blog/home.html', posts = posts, age = age_str(age))
		else:
			return self.render_json(p.as_dict() for p in posts)


		
		
class SignupHandler(BlogHandler):
	def write_form(self, username = "", password = "", verify = "", email = "",
					username_error="", password_error="", verify_error="",
					email_error=""):
		self.render("signup.html", username = username, password = password
					verify = verify, email = email, username_error = username_error
					password_error=password_error,verify_error=verify_error,email_error=email_error)
		
	def get(self):
		self.write_form()
		
	def post(self):
		self.user_username = self.request.get('username')
		self.user_password = self.request.get('password')
		self.user_verify = self.request.get('verify')
		self.user_email = self.request.get('email')
	
		username_error = helpers.check_username(self.user_username)
		password_error = helpers.check_password(self.user_password)
		
		if password_error == '':
			verify_error = helpers.check_verify(self.user_password, self.iser_verify)
		email_error = helpers.check_email(self.user_email)
		
		if (username_error or password_error or verify_error or email_error):
			self.write_form(self.user_username, '', '',
			self.user_email, username_error, password_error, verify_error, email_error)
		else:
			self.done()			

	def done():
		raise NotImplementedError
		
class Unit2Signup(SignupHandler):
	def done(self):
		self.redirect('/welcome?username=%s' % self.user_username)

class BlogSignUp(SignupHandler):
	def done(self):
		u = User.by_name(self.user_username)
		
		if u:
			msg = 'That username already exists'
			self.render('blog/signup.html', username_error = msg)
		
		else:
			u = User.register(self.user_username, self.user_password, self.user_password)
			u.put()
			
			self.login(u)
			self.redirect('blog/welcome')
			
class BlogWelcome(BlogHandler):
	def get(self):
		if self.user:
			self.render('blog/welcome.html', username = self.user.name)
		else:
			self.redirect('blog/signup')
	
class BlogLogin(BlogHandler):
	def get(self)
		self.render('blog/login-form.html')
		
	def post(self):
		username = self.request.get('username')
		password = self.request.get('password')
		
		u = User.login(username, password)
		if u:
			self.login(u)
			self.redirect('blog/welcome')
			
		else:
			msg = 'Invalid login'
			self.render('blog/login-form', error = msg)
			
class BlogLogout(BlogHandler):
	def get(self):
		self.logout()
		self.redirect('blog/home')
		
class BlogNewPost(BlogHandler):
	






	































	






	
