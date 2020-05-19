import illumination_modify
from socket import *
from time import ctime
import RPi.GPIO as GPIO

illumination_modify.initialize()

ctrCmd = ['0','1']

HOST = ''
PORT = 21567
BUFSIZE = 1024
ADDR = (HOST,PORT)

tcpSerSock = socket(AF_INET, SOCK_STREAM)
tcpSerSock.bind(ADDR)
tcpSerSock.listen(5)

while True:
        print 'Waiting for connection'
        tcpCliSock,addr = tcpSerSock.accept()
        print '...connected from :', addr
        try:
                while True:
                        data = ''
                        data = tcpCliSock.recv(BUFSIZE)
                        if not data:
                                break
                        if data == ctrCmd[0]:
                                print 'In up '
                                illumination_modify.increase()
                                print 'Increase: ',illumination_modify.duty
                        if data == ctrCmd[1]:
                                print 'In down'
                                illumination_modify.decrease()
                                print 'Decrease: ',illumination_modify.duty
        except KeyboardInterrupt:
                illumination_modify.close()
                GPIO.cleanup()
tcpSerSock.close();
