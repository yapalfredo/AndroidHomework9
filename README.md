# AndroidHomework9
This 9th homework is called Anti Theft System. The app has BroadcastReceiver and Background Services implemented. <br> <br>
On a case that when the phone is lost, the owner of the phone would be able to track the phone in three ways. <br> <br>
First is when the owner sends in an sms containing a special password and a special command string, like:  <br> 
"1234 SendCallog", the phone will reply to that text with the last 10 most recent outgoing calls. <br> 
Second way is when the owner sends in an sms containing also the same password and another special command string, like:  <br> 
"1234 StartRing", the phone will ring on a full volume right away, and only the owner can stop the ring by sending another command. <br> 
The third way is by tracking the location of the phone. When the owner sends in the same password and special command string, like: <br> 
"1234 StartMonitor", the phone will respond to that text with the last known or updated Latitude and Longitude coordinates. <br>  <br>
Even if the app is not closed or not running, the text commands will still work.  <br>


# The contains:
1 Activity <br> 
2 Background Services (MediaPlayer and Location) <br> 
1 BroadcastReceiver <br>

Given: July 22nd, 2019

# Demos
![Alt text](Screenshots/demo1.gif?raw=true "Send and Receive Call Logs via SMS") <br> 
![Alt text](Screenshots/demo2.gif?raw=true "Send and Receive Location Coordinates via SMS") <br> 
