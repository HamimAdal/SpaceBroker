import ldr
import query
import modify
import maintain
#import top

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
                        
                        temp = data.split(':')

			choice = temp[0]
                        
                                           

			if (choice == '1'):

    					

                                
                                                              
                                x = temp[1] 
                                y = temp[2]  
                                
                                x = int(x)
                                y = int(y)

                                	
			
				query.query(x,y)
    				message = str(query.unitvalue)
                                
                                
				message = sp + message
                                
    				tcpCliSock.sendall(message)
    			        #tcpSerSock.close()  
				break

 
    			time.sleep(2)
    			


                        if (choice == '2'):

                               	

                                
                                
                                x = temp[1] 
                                y = temp[2]  

                                x = int(x)
                                y = int(y)

                                	
			
                                
                                duty_s = temp[3] 
    			        duty_s = int(duty_s)

    				modify.modify(duty_s,x,y)
				break
   			 
                        if (choice == '3'):
                                
                               
                                duty_s = temp[1] 
    			        duty_s = int(duty_s)
                                maintain.maintain(duty_s)

                        
        except KeyboardInterrupt:
                ldr.close()
                GPIO.cleanup()

tcpSerSock.close();






