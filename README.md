# Dynatrace Postcode Custom Maps Example

This repository contains all the example code to demonstrate the creation of custom world maps fed by a UK postcode.


![alt tag](http://i66.tinypic.com/33jmnvk.png)

This uses open source postcode lookups from postcodes.io, OpenStreetMap and leaflet.js to provide the mapping.

## Usage
- The JAR file waits for a user to enter a postcode. Instrument the JAR with AppMon and create an argument sensor on the `submitPostcode` method.
- Create a Business Transaction which splits by the argument value and add this to a dashboard (see: `PostcodeDashboard.dashboard.xml`). - Create a new dashboard (this is the one you'll actually use. See: `Postcode Visual Dashboard.dashboard.xml`).
- Deploy the WAR file to your favourite Java app server (JBoss / Tomcat / Resin).
