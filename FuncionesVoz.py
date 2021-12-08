import speech_recognition as sr
from gtts import gTTS 
from playsound import playsound
from pygame import mixer

def tts(text_file,lang,name_file):
	with open(text_file, 'r') as archiv:
		text = archiv.read()
	archiv = gTTS(text=text, lang=lang)
	filename=name_file
	archiv.save(filename)
