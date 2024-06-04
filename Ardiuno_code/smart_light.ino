#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ArduinoJson.h>

const char* ssid = "light";
const char* password = "87654321";

int pirPin = 5;
int ledPin = 4;
int ldrPin = A0;
ESP8266WebServer server(80);

bool ledState = false;
bool pirState = false;
bool ldrState = false;
String recieved = "null";
void setup() {
    pinMode(ledPin, OUTPUT);
    pinMode(pirPin, INPUT);
    pinMode(ldrPin, INPUT);
    Serial.begin(9600);
    Serial.print("Hello");
    
    WiFi.softAP(ssid, password);
    Serial.println("Access Point Started");

    // Define endpoints
    server.on("/led", HTTP_POST, handleLed);
    server.onNotFound(handleNotFound);

    server.begin();
    Serial.println("HTTP server started");

}

void loop() {  
  server.handleClient();
  if(ledState){
    digitalWrite(ledPin, HIGH);
    Serial.println("ledStatus:  ON");
  }
  else if(pirState && digitalRead(pirPin)){
    Serial.println("pirStatus:  ON");
    digitalWrite(ledPin, HIGH);
  }

  else if(ldrState && digitalRead(ldrPin) <= 200){
    Serial.println("ldrStatus:  ON");
    digitalWrite(ledPin, HIGH);
  }
  else{
    digitalWrite(ledPin, LOW);
  }
  int a = analogRead(ldrPin);
  Serial.println(a);
}

void handleLed() {
  
    String message = "";
    DynamicJsonDocument jsonBuffer(200);
    
    // Parse request
    if (server.hasArg("plain")) {
        message = server.arg("plain");
        recieved = message;
        Serial.println(message);
        deserializeJson(jsonBuffer, message);
        String ledStatus = jsonBuffer["led"]["status"];
        String pirStatus = jsonBuffer["led"]["pir_status"];
        String ldrStatus = jsonBuffer["led"]["ldr_status"];

        ledState = (ledStatus == "ON");
        pirState = (pirStatus == "ON");
        ldrState = (ldrStatus == "ON");

        // Send response
        server.send(200, "text/plain", "LED is now " + String(ledState ? "ON" : "OFF"));
    } else {
        server.send(400, "text/plain", "Bad Request");
    }
}
void handleNotFound() {
    server.send(404, "text/plain", "Not Found");
}
