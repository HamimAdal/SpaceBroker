import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)



led = 18 	#led is connected with pin number 18
led1 = 23 	#led1 is connected with pin number 23


def initialize():       
        
    GPIO.setup(led, GPIO.OUT) 	
    GPIO.setup(led1, GPIO.OUT) 	
    
    global pwm_led
    global pwm_led1

    pwm_led = GPIO.PWM( led, 50)  
    pwm_led.start(55)

    pwm_led1 = GPIO.PWM( led1, 50)  
    pwm_led1.start(0) 


 
 
def close():
    pwm_led.stop()
    pwm_led1.stop()






















