import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)

led = [18,23]
	

def initialize():   

    for x in led:
    	
    	 GPIO.setup(x, GPIO.OUT) 	
          
    global pwm_led
    pwm_led = [0,0]
    i=0 

    for x in led:
     	
    	 pwm_led[i] = GPIO.PWM( x, 50)  

         if i==0:
            
             pwm_led[i].start(55)
         else:

             pwm_led[i].start(0) 

         i = i+1

def close():

    for x in pwm_led:   	
         x.stop()
    

























