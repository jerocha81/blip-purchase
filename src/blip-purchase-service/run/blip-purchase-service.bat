@echo off

cd ..
java -classpath target\lib\*;target\blip-purchase-service-1.0.0.jar pt.blip.blippurchaseservice.BlipPurchase server
