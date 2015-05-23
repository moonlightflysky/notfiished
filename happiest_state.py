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
	
def getStateHappiness(tweets, sentiment):
	
	state_list = {}
	
	for index in range(len(tweets)):
		
		
		
		if (tweets[index].has_key("text") and tweets[index].has_key("place")):
			if not (tweets[index]["place"] == None):
				words = tweets[index]["text"].split()
				state = tweets[index]["place"][country_code]
				
				sent_score = 0
				
				for word in words:
					word = word.rstrip('?:!.,;"!@')
					word = word.replace("\n", "")
					word = word.encode('utf-8', 'ignore')
					
					if (word != ""):
						if word in sentiment.keys():
							sent_score += float(sentiment[word])
				
				state = state.encode('utf-8', 'ignore')
				if (state_list.has_key(state)):
					state_list.append(sent_score)
				else:
					state_list[state] = []
					state_list.append(sent_score)
		
	state_sentiment = {}
	for key in state_list.keys():
		total_num = len(state_list[key])
		total_val = sum(state_list[key])
		ave_score = float(total_val / total_num)
		
		state_sentiment[key] = ave_score
	
	return state_sentiment
	
def main():
	tweets = tweet_dict(tData)
	sentiment = sentiment_dict(sData)
	state_sentiment = getStateHappiness(tweets, sentiment)
	
	happiest_state = ""
	maxscore = 0
	
	for key in state_sentiment.keys():
		if (happiest_state == "" or state_sentiment[key] > maxscore ):
			happiest_state == key
			maxscore = state_sentiment[key]
	output_score = ".3%f" % maxscore
	print happiest_state + " " + output_score
		
