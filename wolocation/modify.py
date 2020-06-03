import ldr
import RPi.GPIO as GPIO
import time


GPIO.setmode(GPIO.BCM)


value = 0 	# this variable will be used to store the ldr value
ldrpin = [4] 	#ldr is connected with pin number 4

duty = [55,0]
i = 0


def increase ():
   

    global duty
    global i

   
    if duty[i] <= 0 :
         duty[i] = 0 
         if i > 0:
         	i = i-1
        	duty[i] -= 5
    else:
         duty[i] -= 5

    j=0
    for x in duty:
    	
    	 ldr.pwm_led[j].ChangeDutyCycle(x)  
         j=j+1

    time.sleep(0.5) 


def decrease ():

    global duty
    global i
    
    if duty[i] >= 100 :
         duty[i] = 100 
         if i < len(duty) - 1:
         	i = i+1
        	duty[i] += 5

    else:
         duty[i] += 5

    j=0
    for x in duty:
    	
    	 ldr.pwm_led[j].ChangeDutyCycle(x)  
         j=j+1
    
  
    time.sleep(0.5) 


def modify (duty_s):
       
    global value
    global duty
    
    duty_s = 1000 - duty_s   

    lower = duty_s - 10
    upper = duty_s + 10

    while True:

	value = rc_time(ldrpin[0]) * 1000
    	
        original_value = value
	value = 1000 - value
	if value < 0:
       	        value = 0
	print("\n")
	
	print("Current illumination level: {} ".format(value) )   
        
        j=0
        for x in duty:
                print("Current LED{} Value: {} ".format(j+1,x))  
                j=j+1

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
        j=0
	for x in duty:
    	
    	        ldr.pwm_led[j].ChangeDutyCycle(x)  
                j=j+1
  
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





