#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <WiFiClient.h>

const char *ssid = "Waince2";
const char *password = "12341234";

// Define the pin number for the built-in LED
const int LED_PIN = 4;  
const int LDR_PIN = A0;  
int ldrValue ;

// WiFiServer server(80);

void setup() {
  Serial.begin(115200);
  pinMode(LED_PIN, OUTPUT);

  WiFi.softAP(ssid, password);

//   // Connect to Wi-Fi
//   Serial.println();
//   Serial.print("Connecting to ");
//   Serial.println(ssid);
//   WiFi.begin(ssid, password);
//   while (WiFi.status() != WL_CONNECTED) {
//     delay(500);
//     Serial.print(".");
//   }
//   Serial.println("");
//   Serial.println("WiFi connected");

//   // Start the server
//   server.begin();
//   Serial.println("Server started");

//   // Print the IP address
//   Serial.println(WiFi.localIP());
 
}

void loop() {
   int numClients = WiFi.softAPgetStationNum();
  Serial.print("Number of connected clients: ");
  Serial.println(numClients);

  delay(5000); // Check every 5 seconds

//   // Check if a client has connected
//   WiFiClient client = server.available();
//   if (!client) {
//     return;
//   }

//   // Wait until the client sends some data
//   while (!client.available()) {
//     delay(1);
//   }

//   // Read the first line of the request
//   String request = client.readStringUntil('\r');
//   Serial.println(request);
//   client.flush();

//   // Control the LED based on the request
//   if (request.indexOf("/on") != -1) {
//     digitalWrite(LED_PIN, HIGH);
//   } else if (request.indexOf("/off") != -1) {
//     digitalWrite(LED_PIN, LOW);
//   }
}


 
  //  = analogRead(LDR_PIN);
  // if(ldrValue > 1){
  //   digitalWrite(LED_PIN,HIGH);
  // } else{
  //   digitalWrite(LldrValueED_PIN,LOW);
  // }
  // Serial.println(ldrValue);

    // digitalWrite(LED_PIN,HIGH);
  // delay(1000);
  // digitalWrite(LED_PIN,LOW);
  // delay(1000);