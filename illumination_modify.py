import RPi.GPIO as GPIO
import time

duty = 30

def initialize():     
    led = 18  
    GPIO.setmode( GPIO.BCM)  
    GPIO.setup( led, GPIO.OUT)  
    global pwm_led
    pwm_led = GPIO.PWM( led, 50)  
    pwm_led.start(30) 
		
def increase():
    global duty
    duty += 10
    if duty > 100:
           duty = 100  
    pwm_led.ChangeDutyCycle(duty)  
    time.sleep(0.5) 

def decrease():
    global duty
    duty -= 10
    if duty < 0:
           duty = 0
    pwm_led.ChangeDutyCycle(duty)  
    time.sleep(0.5) 

def close():
    pwm_led.stop()
if __name__ == '__main__':
    initialize()
