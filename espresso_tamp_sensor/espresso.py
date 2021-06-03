#!/usr/bin/env python

import tkinter as tk
from tkinter import *
import time
import sys
import os
import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BCM)

# read SPI data from MCP3008 chip, 8 possible adc's (0 thru 7)
def readadc(adcnum, clockpin, mosipin, misopin, cspin):
        if ((adcnum > 7) or (adcnum < 0)):
                return -1
        GPIO.output(cspin, True)

        GPIO.output(clockpin, False)  # start clock low
        GPIO.output(cspin, False)     # bring CS low

        commandout = adcnum
        commandout |= 0x18  # start bit + single-ended bit
        commandout <<= 3    # we only need to send 5 bits here
        for i in range(5):
                if (commandout & 0x80):
                        GPIO.output(mosipin, True)
                else:
                        GPIO.output(mosipin, False)
                commandout <<= 1
                GPIO.output(clockpin, True)
                GPIO.output(clockpin, False)

        adcout = 0

        # read in one empty bit, one null bit and 10 ADC bits
        for i in range(12):
                GPIO.output(clockpin, True)
                GPIO.output(clockpin, False)
                adcout <<= 1
                if (GPIO.input(misopin)):
                        adcout |= 0x1

        GPIO.output(cspin, True)
        global pause
        
        adcout >>= 1       # drop first null bit
        return adcout



class Display(tk.Frame):
        def __init__(self):
                pause = 2
                tk.Frame.__init__(self)
                
                self.textLabel = Label(self, text="Espresso Tamp Measurement", justify=CENTER)
                self.textLabel.pack(side=TOP)
                
                self.menu = tk.Button(self,text="Start", command=self.start, background = 'black', fg='white')
                self.menu.pack(side=LEFT)

                self.menu = tk.Button(self, text="Stop", command=self.stop, background = 'black', fg='white')
                self.menu.pack(side=LEFT)
                
                
                photo = PhotoImage(file="unnamed.png")
                self.imgLabel = Label(image=photo)
                self.imgLabel.photo=photo
                self.imgLabel.pack(side=BOTTOM)
               
                
                self.menu = tk.Button(self, text="Exit", command=self.exit, background = 'black', fg='white')
                self.menu.pack(side=RIGHT)

                self.output = tk.Text(self, width=100, height=15, background = 'black', fg='white')
                self.output.pack(side=tk.LEFT)

                self.scrollbar = tk.Scrollbar(self, orient="vertical", command = self.output.yview)
                self.scrollbar.pack(side=tk.RIGHT, fill="y")

                self.output['yscrollcommand'] = self.scrollbar.set

                self.count = 1
                self.configure(background='black')
                self.pack()
        
                

        def start(self):
                pause = 0
                                
                # SPI pins ADC -> Cobbler
                SPICLK = 18
                SPIMISO = 23
                SPIMOSI = 24
                SPICS = 25

                # SPI interface pins
                GPIO.setup(SPIMOSI, GPIO.OUT)
                GPIO.setup(SPIMISO, GPIO.IN)
                GPIO.setup(SPICLK, GPIO.OUT)
                GPIO.setup(SPICS, GPIO.OUT)

                # 10k potentiometer connected to adc #0
                potentiometer_adc = 0;

                last_read = 0
                if (self.count < 1000 and pause == 0):
                        trim_pot = readadc(potentiometer_adc, SPICLK, SPIMOSI, SPIMISO, SPICS)
                        last_read = trim_pot
                        self.write(str('lbs pressure: {:.1f}'.format(last_read*.35)) + '\n')
                        print("lbs pressure: {:.1f}".format(last_read*.35))
                        time.sleep(0.2) #sleep before repeat
                        self.after(20, self.start)

        def stop(self):
                pause = 1                
                        
        def exit(self):
                exit()
                
                
        def write(self, txt):
                self.output.insert(tk.END,str(txt))
                self.output.see("end")
                self.update_idletasks()


if __name__ == '__main__':
        Display().mainloop()
