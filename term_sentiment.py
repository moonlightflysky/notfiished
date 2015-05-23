import json
import sys

sData = sys.argv[1]
tData = sys.argv[2]

def tweet_dict(tData):
	
	t_list_dict = []
	t_file = open(tData)
	for line in t_file:
		t_list_dict.append(json.load(line))
	return t_list_dict

def sentiment_dict(sData):
	
	scores = {}
	s_file = open(sData)
	
	for line in s_file:
		term, score = line.split("\t")
		scores[term] = float(score)
	return scores

def main():
	tweets = tweet_dict(tData)
	sentiment = sentiment_dict(sData)
	term_dict = {}
	
	accum_term = {}
	term_dict
	
	for index in range(len(tweets)):
		if tweets[index].has_key("text"):
			words = tweets[index]["text"].split()
			sent_score = 0
			termlist = []
			
			for word in words:
				word = word.rstrip('?:!.,;"!@')
				word = word.replace("\n", "")
				
				word = word.encode('utf-8', 'ignore')
				
				if (word != ""):
					if ( word in sentiment.keys()):
						sent_score += float(sentiment[word])
				
					else:
						
						if not (word in term_list):
							term_list.append(word)
			
			
			for word in term_list:
				
				if not accum_term.has_key(word):
					accum_term[word] = []
				accum_term[word].append(sent_score)	
				
	for key in accum_term.keys():
		
		total_score = 0;
		term_value = 0;
		for score in accum_term[key]:
			total_score = total_score + score
		term_value = total_score / len(accum_term[key])
		term_dict[key] = term_value
		
		adjusted_score = "%.3f" % term_value
		print key + " " + adjusted_score
		
					
					
	
	
	
	
