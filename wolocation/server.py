import ldr
import query
import modify
import maintain

from socket import *
import time
import RPi.GPIO as GPIO



ldr.initialize()



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

			sp = data[0] + data[1]
  			data = data [2:]
			choice = data[0]
                        data = data [1:]
                                           

			if (choice == '1'):

    								
				query.query()
    				message = str(query.unitvalue)
				message = sp + message
    				tcpCliSock.sendall(message)
    				#tcpSerSock.close()  
				break

 
    			time.sleep(2)
    			duty_s = data
    			duty_s = int(duty_s)


                        if (choice == '2'):

    				modify.modify(duty_s)
				break
   			 
                        if (choice == '3'):

    				maintain.maintain(duty_s)

                        
        except KeyboardInterrupt:
                ldr.close()
                GPIO.cleanup()
tcpSerSock.close();






