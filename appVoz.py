import sys
from PyQt5.uic import loadUi
from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QFileDialog, QMainWindow, QApplication
from PyQt5.uic.uiparser import QtWidgets
import speech_recognition as sr
from gtts import gTTS 
from playsound import playsound
from pygame import mixer

from FuncionesVoz import controlVoz, tts



class voz(QMainWindow):

    def __init__(self):
        super(voz,self).__init__()
        loadUi("UIVoz.ui", self)
        self.abriArchivo.clicked.connect(self.cargarArchivo)
        self.btnLeer.clicked.connect(self.leer)
        self.btnPausar.clicked.connect(self.pausar)
        self.btnReproducir.clicked.connect(self.reproducir)

        self.btnLeer.setEnabled(False)
        self.btnPausar.setEnabled(False)
        self.btnReproducir.setEnabled(False)

        controlVoz


    def cargarArchivo(self):
        fname=QFileDialog.getOpenFileName(self, 'Open file', 'C:\\Users', 'Texto (*.pdf, *.txt)')
        self.nombreArchivo.setText(fname[0])
        tts(fname[0],'es','audio_prueba.mp3')
        self.btnLeer.setEnabled(True)
    
    def leer(self):
        mixer.init()
        mixer.music.load('audio_prueba.mp3')
        mixer.music.play()
        self.btnPausar.setEnabled(True)
        self.btnLeer.setEnabled(False)


    def pausar(self):
        mixer.music.pause()
        self.btnReproducir.setEnabled(True)
        self.btnPausar.setEnabled(False)


    def reproducir(self):
        mixer.music.unpause()
        self.btnPausar.setEnabled(True)
        self.btnReproducir.setEnabled(False)

app = QApplication(sys.argv)
GUI = voz()
GUI.show()
sys.exit(app.exec_())

