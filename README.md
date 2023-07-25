# GloboFly-Retrofit-Demo

Its a demo application of retrofit CRUD implementation using local server.

---


## Requirments
1. Android Nougat (7.1.1) or above


## How to run this project
- Download and install the latest "Android Studio" IDE.
- Download and install latest "Node.js" LTS.
- Clone this repository and open the project folder using Android Studio.
- create a folder named "server" (or any name you like) anywhere in your PC.
- create a text document inside the "server" folder and rename it as "server.js" (name can be anything but extension must be .js)
- open the "server.js" file with any text editor you like (Visual Studio Code, Notepad++, etc) and paste the code given below.

    ```javascript
    var express = require('express');
    var app = express();
    var fs = require("fs");
    
    var bodyParser = require('body-parser')
    app.use(bodyParser.json()); 
    app.use(bodyParser.urlencoded({
        extended: true
    }));
    
    //Arbitrary ID manager since we don't use a database
    var index = 5;
    
    // Initializing Destinations Array.. It will behave like a dummy database 
    var destinations = [{
        "id": 1,
        "city": "Mumbai",
        "description": "Mumbai, the vibrant megacity on India's west coast, captivates with its unique blend of tradition and modernity. As the country's financial and entertainment hub, it pulsates with an electrifying energy. Iconic landmarks like the Gateway of India and Marine Drive adorn the coastline, while the bustling local markets and street food stalls tempt the senses with a rich array of flavors. Amidst the bustling chaos, Mumbai's spirit thrives in the warmth of its diverse people. From the historical sites to the glitzy Bollywood scene, Mumbai beckons visitors with its kaleidoscope of culture, making it a city that never fails to leave an indelible mark on the heart.",
        "country" : "India"
    }, {
        "id": 2,
        "city": "Melbourne",
        "description": "Melbourne, the cultural capital of Australia, exudes a captivating charm that lures visitors from around the globe. This dynamic city effortlessly balances its rich heritage with a modern, cosmopolitan vibe. Gleaming skyscrapers mingle with elegant Victorian-era architecture, creating a picturesque urban landscape. Known for its vibrant arts scene, Melbourne hosts numerous galleries, theaters, and street art that adorn its laneways. The city's culinary delights are a gastronomic adventure, from trendy cafes to world-class restaurants. Nature enthusiasts can explore nearby parks and the iconic Yarra River. With its friendly locals and a calendar full of events, Melbourne promises an unforgettable experience in every season.",
        "country" : "Australia"
    }, {
        "id": 3,
        "city": "Washington DC",
        "description": "Washington, D.C., the political heart of the United States, is a city steeped in history and grandeur. Its iconic landmarks, including the majestic Capitol Building, the White House, and the Lincoln Memorial, stand as testaments to the nation's past and its enduring ideals. Stately museums and galleries, such as the Smithsonian Institution, showcase priceless treasures and cultural wonders. The city's elegant avenues and parks, like the National Mall, invite reflection and contemplation. Beyond politics, D.C. thrives with a diverse community, offering vibrant neighborhoods, world-class restaurants, and bustling markets. Washington, D.C. embodies the essence of the nation, embodying the spirit of democracy and the pursuit of progress.",
        "country" : "USA"
    }, {
        "id": 4,
        "city": "New Delhi",
        "description": "New Delhi, the bustling capital of India, is a captivating metropolis that fuses ancient history with modernity. Adorned with architectural marvels like the Red Fort, India Gate, and Humayun's Tomb, the city reflects the grandeur of its Mughal past. New Delhi's vibrant markets, such as Chandni Chowk, offer a sensory overload of colors, aromas, and flavors, while its upscale malls cater to modern tastes. The seat of India's government, the city boasts stately government buildings, including Rashtrapati Bhavan and Parliament House. Amidst the chaotic charm, New Delhi's people embrace their cultural diversity, making it an enchanting destination that encapsulates the nation's essence.",
        "country" : "India"
    }, {
        "id": 5,
        "city": "Tokyo",
        "description": "Tokyo, the pulsating capital of Japan, is a mesmerizing blend of ancient traditions and cutting-edge modernity. This sprawling metropolis thrives with a symphony of neon lights, towering skyscrapers, and a myriad of bustling neighborhoods. From the serene gardens of the Imperial Palace to the vibrant streets of Shibuya and Shinjuku, Tokyo offers an unforgettable sensory experience. The city's world-renowned culinary scene tantalizes taste buds with sushi, ramen, and other delectable delights. Amidst the fast-paced lifestyle, Tokyo cherishes its deep-rooted culture, evident in traditional temples, tea ceremonies, and festivals. Embracing innovation while preserving its heritage, Tokyo stands as a dynamic city that enthralls and captivates all who visit.",
        "country" : "Japan"
    }]
    
    // A promo message to user 
    var message = "Black Friday! Get 50% cachback on saving your first spot.";
    
    app.get('/messages', function (req, res) {
        res.end(JSON.stringify(message));
    })
    
    // Get the list of destinations, convert it to JSON and send it back to client 
    app.get('/destination', function (req, res) {
        var count = req.query.count != undefined ? req.query.count : req.query.count = 100;
        if(req.query.country){
            var countrySpots = destinations.filter(function(destination) {
                return destination.country == req.query.country
            });
            res.end(JSON.stringify(countrySpots.slice(0, count)));
        }
        
        res.end(JSON.stringify(destinations.slice(0, count)));
    })
    
    // Get one particular Destination using ID 
    app.get('/destination/:id', function (req, res) {
        for (var i = 0; i < destinations.length; i++) {
            if(destinations[i].id == req.params.id){
                res.end(JSON.stringify(destinations[i]));
            }
        }
    })
    
    // Create a new Destination and add it to existing Destinations list 
    app.post('/destination', function (req, res) {
        var newDestination = {
            "city": req.body.city,
            "description": req.body.description,
            "country" : req.body.country,
            "id": index + 1
        }
    
        index++;
    
        destinations.push(newDestination);
        res.status(201).end(JSON.stringify(newDestination));
    })
    
    // Update a Destination 
    app.put('/destination/:id', function (req, res) {
        var destination;
        for (var i = 0; i < destinations.length; i++) {
            if(destinations[i].id == req.params.id){
                destinations[i].city = req.body.city;
                destinations[i].country = req.body.country;
                destinations[i].description = req.body.description;
                destination = destinations[i];
            }
        }
    
        res.end(JSON.stringify(destination));
    })
    
    // Delete a Destination 
    app.delete('/destination/:id', function (req, res) {
        for (var i = 0; i < destinations.length; i++) {
            if(destinations[i].id == req.params.id){
                destinations.splice(i, 1);
                res.status(204).end(JSON.stringify(destinations[i]));
            }
        }
    });
    
    // Home Page 
    app.get('/', (req, res) => res.send('Welcome! You are all set to go!'))
    
    // Configure server 
    var server = app.listen(9000, '127.0.0.1', function (req, res) {
    
        var host = server.address().address
        var port = server.address().port
    
        console.log(`Server running at http://${host}:${port}/`);
    })
    ```
- Save the "server.js" file.
- create another text document inside the "server" folder and rename it as "package.json" then paste the code given below.

    ```json
    {
    "name": "MyNodeApp",
    "version": "1.0.0",
    "description": "The node api for retrofit",
    "main": "server.js",
    "dependencies": {
      "body-parser": "^1.18.2",
      "express": "^4.16.1"
    },
    "devDependencies": {},
    "scripts": {
      "test": "echo \"Error: no test specified\" && exit 1",
      "start": "node server.js"
    },
    "repository": {},
    "author": "Smartherd",
    "license": "ISC"
    }
    ```
- Save the "package.json" file.
- Open cmd or powershell in that directory ("server" folder). Run the command given below.
  ```bash
  npm install
  ```
- After the execution of the command finishes, run the command given below on the same directory ("server" folder) to start the local server.
  ```bash
  node server.js
  ```
- Run the project using **Android Studio Emulator**



## Technology used:
- JDK : jbr-17 (JetBrains Runtime version 17.0.6)
- Language : Kotlin
- IDE : Android Studio Flamingo | 2022.2.1 Patch 2
- Back-end : local server (node)


## Gradle (app):
- compileSdk 33
- minSdk 25


## Features:
  - HTML CRUD operations
  

## Screenshots:
- Welcome Screen
![1](https://github.com/touhid-96/GloboFly-Retrofit-Demo/blob/main/app/src/main/res/screenshots/welcome.png)

- Home (RecyclerView)
![2](https://github.com/touhid-96/GloboFly-Retrofit-Demo/blob/main/app/src/main/res/screenshots/home.png)

- Add Destination
![3](https://github.com/touhid-96/GloboFly-Retrofit-Demo/blob/main/app/src/main/res/screenshots/add.png)

- Edit & Delete
![4](https://github.com/touhid-96/GloboFly-Retrofit-Demo/blob/main/app/src/main/res/screenshots/edit_delete.png)
