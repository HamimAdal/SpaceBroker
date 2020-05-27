import ldr
import RPi.GPIO as GPIO
import time



GPIO.setmode(GPIO.BCM)

 
duty= 55
duty1=0

value = 0 	# this variable will be used to store the ldr value
ldrpin = 4 	#ldr is connected with pin number 4



def increase ():
    global duty
    global duty1
    duty -= 5

    if duty < 0:
            duty = 0 
	    duty1 -= 5
    if duty1 < 0:
            duty1 = 0 

    ldr.pwm_led.ChangeDutyCycle(duty) 	
    ldr.pwm_led1.ChangeDutyCycle(duty1)  
    time.sleep(0.5) 

def decrease ():
    global duty
    global duty1
    duty += 5

    if duty > 100:
            duty = 100  
            duty1 += 5

    if duty1 > 100:
            duty1 = 100  
			

    ldr.pwm_led.ChangeDutyCycle(duty)  
    ldr.pwm_led1.ChangeDutyCycle(duty1)  
    time.sleep(0.5) 



def modify (duty_s):
       
    global value
    global duty
    global duty1

    duty_s = 1000 - duty_s
  
    lower = duty_s - 10
    upper = duty_s + 10

    while True:

	value = rc_time(ldrpin) * 1000
    	
        original_value = value
	value = 1000 - value
	if value < 0:
       	        value = 0
	
	print("Current illumination level: {} and Current LED1 Value: {} and Current LED2 Value: {}".format(value,duty,duty1))    
       	value = original_value
        
				
        if (value > upper):			
        	decrease()

	if (value >= lower and value <= upper):
                
		print(" MODIFIED LDR VALUE ACHIEVED :")
		
		original_value = value
		value = 1000 - value
    		
		print(value)
		value = original_value
	
		break

	if (value < lower):
                
		increase()

    while True:
        
	ldr.pwm_led.ChangeDutyCycle(duty)  
	ldr.pwm_led1.ChangeDutyCycle(duty1)  
	time.sleep(0.5) 


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





