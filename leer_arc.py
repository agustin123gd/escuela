import speech_recognition as sr
from gtts import gTTS 
from playsound import playsound
from pygame import mixer

#esta funcion pasa el archivo de texto a mp3
def tts(text_file,lang,name_file):
	with open(text_file, 'r') as archiv:
		text = archiv.read()
	archiv = gTTS(text=text, lang=lang)
	filename=name_file
	archiv.save(filename)
	
mixer.init()
texto=str(input("Selecciona la texto: "))
tts(texto,'es','audio_prueba.mp3')
mixer.music.load('audio_prueba.mp3')
mixer.music.play()

while True:
	print("Pulsa s para hablar")
	opcion = input(">>> ")
	if opcion == "s":
		while True:
			print("Di pausa para detener")
			print("Di reproducir para continuar")
			print("Di siguiente para elegir otro texto")
			print("Dí fin para detener")
			r = sr.Recognizer()
			with sr.Microphone(device_index = 0) as source:	
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
