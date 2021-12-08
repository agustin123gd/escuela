import sys
from PyQt5.QtGui import QIcon
from PyQt5.uic import loadUi
from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QFileDialog, QMainWindow, QApplication
from PyQt5.uic.uiparser import QtWidgets
import speech_recognition as sr
from gtts import gTTS 
from playsound import playsound
from pygame import mixer
from os import remove
import threading

from FuncionesVoz import tts



class voz(QMainWindow):
    cont=0
    estado='apagado'
 
    def __init__(self):
        super(voz,self).__init__()
        loadUi("UIVoz.ui", self)
        self.abriArchivo.clicked.connect(self.cargarArchivo)
        self.btnLeer.clicked.connect(self.leer)
        self.btnPausar.clicked.connect(self.pausar)
        self.btnReproducir.clicked.connect(self.reproducir)
        self.btnTerminar.clicked.connect(self.terminar)
        self.btnMicro.clicked.connect(self.micro)

        self.btnLeer.setEnabled(False)
        self.btnPausar.setEnabled(False)
        self.btnReproducir.setEnabled(False)
        self.btnTerminar.setEnabled(False)

    def micro(self):
        thread = threading.Thread(target=self.funcionesVoz())
        if(self.estado=='apagado'):   
            thread.start()    
            self.btnMicro.setIcon(QIcon('cerrado.png')) 
            self.estado='apagado'
        else:
            self.btnMicro.setIcon(QIcon('abierto.png'))
            self.cont=1   

    def cargarArchivo(self):
        fname=QFileDialog.getOpenFileName(self, 'Open file', 'C:\\', 'Texto (*.txt)')
        self.nombreArchivo.setText(fname[0])
        tts(fname[0],'es','audio.mp3')
        self.btnLeer.setEnabled(True)
        self.cont=0
    
    def funcionesVoz(self):
        while self.cont == 0:
            r = sr.Recognizer()	
            with sr.Microphone() as source:
                print("Te estoy escuchando...")
                audio = r.listen(source)
                try:
                    opcion = r.recognize_google(audio, language = 'es-ES')
                    print('Tú dijiste: {}'.format(opcion))
                    if opcion == "pausa":
                        self.pausar()
                    elif opcion == "reproducir":
                        self.reproducir()
                    elif opcion == "terminar": 
                        self.terminar()
                    else:
                        print('Error, tú dijiste: {}'.format(opcion))
                except:
                    print("no fue posible")
    
    def leer(self):
        mixer.init()
        mixer.music.load('audio.mp3')
        mixer.music.play()   
        self.btnPausar.setEnabled(True)
        self.btnTerminar.setEnabled(True)
        self.btnLeer.setEnabled(False)
        self.abriArchivo.setEnabled(False)

    def pausar(self):
        mixer.music.pause()
        self.btnReproducir.setEnabled(True)
        self.btnPausar.setEnabled(False)


    def reproducir(self):
        mixer.music.unpause()
        self.btnPausar.setEnabled(True)
        self.btnReproducir.setEnabled(False)
    
    def terminar(self):
        mixer.music.stop()
        mixer.quit()
        self.abriArchivo.setEnabled(True)
        self.btnTerminar.setEnabled(False)
        self.btnReproducir.setEnabled(False)
        self.nombreArchivo.setText('')
        self.cont=1

app = QApplication(sys.argv)
GUI = voz()
GUI.show()
sys.exit(app.exec_())
