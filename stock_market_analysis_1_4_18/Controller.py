import nn as nn
from stock import Stock
import stock_data as sd
import getSP500 as sp
from top10 import top10
import time
import datetime
import os

if os.geteuid() != 0:
    exit("You need to have root privileges to run this script.\nPlease try again, this time using 'sudo'. Exiting.")

run_this = True
while True:
    now = datetime.datetime.now()
    if now.hour >= 0 and now.hour < 16:
        run_this = True
    
    if now.hour >= 16 and run_this == True:
        nn.run()
        run_this = False

    time.sleep(3550)
