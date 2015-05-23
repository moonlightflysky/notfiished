import json
import sys

tData = sys.argv[1]

def tweet_dict(tData):
	t_list_dict = []
	t_file = open(tData)
	
	for line in t_file:
		t_list_dict.append(json.load(line))
	return t_list_dict
	
def countWords(tweets):
	
	frequency = {}
	
	for index in range(len(tweets)):
		if (tweets[index].has_key("text")):
			words = tweets[index]["text"].split()
			
			for word in words:
				word = word.rstrip('?:!.,;"!@')
				word = word.replace("\n", "")
				word = word.encode('utf-8', 'ignore')
				
				if not (word == ""):
					if (frequency.has_key(word)):
						frequency[word] += 1
					else:
						frequency[word] = 1
	return frequency
	
def main():
	tweets = tweet_dict(tData)
	frequency = countWords(tweets)
	output_list = {}
	
	total_num = 0
	
	for value in frequency.values():
		total_num += value
	
	for key in frequency.keys():
		output_list[key] = ".3%f" % float(frequency[key] / total_num)
		print key + " " + output_list[key]
	
	
