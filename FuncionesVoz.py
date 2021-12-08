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

def controlVoz():
	with sr.Microphone(device_index = 0) as source:
		r = sr.Recognizer()	
		audio = r.listen(source)
		try:
			opcion = r.recognize_google(audio, language = 'es-ES')
			print('Tú dijiste: {}'.format(opcion))
			if opcion == "pausa":
				mixer.music.pause()
			elif opcion == "reproducir":
				mixer.music.unpause()
			elif opcion == "fin": 
				mixer.music.stop()
			elif opcion == "siguiente":
				mixer.stop()
			 	#texto=str(input("Selecciona la texto: "))
				#tts(texto,'es','audio_prueba.mp3')
				#mixer.music.load('audio_prueba.mp3')
				#mixer.music.play()
			else:
				print('Error, tú dijiste: {}'.format(opcion))
		except:
			print("no fue posible")