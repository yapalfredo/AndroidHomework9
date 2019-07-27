# AndroidHomework
This 9th homework is called Anti Theft System. The app has BroadcastReceiver and Background service implemented.
On a case that when the phone is lost, the owner of the phone would be able to track this phone in three ways.
First is when the owner sends in an sms containing a special password and a special command string, like: 
"1234 SendCallog", the phone will reply to that text with the last 10 most recent outgoing calls.
Second way is when the owner sends in an sms containing also the same password and another special command string, like:
"1234 StartRing", the phone will ring on a full volume right away, and only the owner can stop the ring by sending another command.
The third way is by tracking the location of the phone. When the owner sends in the same password and special command string, like:
"1234 StartMonitor", the phone will respond to that text with the last known or updated Latitude and Longitude coordinates.


# The contains:
1 activity
2 Background services (MediaPlayer and Location)
3 BroadcastReceiver

Given: July 22nd, 2019

# Demo
![Alt text](Screenshot/app.gif?raw=true "demo")
