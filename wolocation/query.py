import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BCM)


value = 0 	# this variable will be used to store the ldr value
ldr = 4 	#ldr is connected with pin number 4



def rc_time (ldr):
   
 
        #Output on the pin for

    GPIO.setup(ldr, GPIO.OUT)
    GPIO.output(ldr, GPIO.LOW)
    time.sleep(0.1)
 
        #Change the pin back to input

    GPIO.setup(ldr, GPIO.IN)
    currentTime = time.time()
    diff = 0
 
        #Count until the pin goes high


    while (GPIO.input(ldr) == GPIO.LOW):
        diff  = time.time() - currentTime
 
    return diff



def query ():
    
    global value
    global unitvalue

    time.sleep(3)
    value = rc_time(ldr) * 1000

    original_value = value
    value = 1000 - value

    if value < 0:
        value = 0

    unitvalue= value

    print("\nCurrent illumination level: {} unit".format(value))
    
    













