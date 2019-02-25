# NominatimReverseGeocodingJAPI
Reverse geocoding library for Java (using Nominatim)
You can use it as library in your own projects or as command line tool.

## Command line
Necessary parameters are -lat and -lon for latitude and longitude. The simplest possible call looks like this:
```
java -jar reverseGeocoding.jar -lat 57.1653392 -lon -2.1056118
```
and the result will look like this:
```
Sir Duncan Rice Library, Bedford Road, Kittybrewster, Aberdeen, Aberdeen City, S cotland, AB24 3AA, United Kingdom
```

There are two optional parameters: With -zoom and a number between 0 and 18 you can select the level of detail of the result, where 0 means only the country will be returned and 18 means the full address, including building name or street number, will be returned. The second parameter is -osm, which will add the OpenStreetMap ID and type of the returned entity to the result.

## Library
If you want to use it as a library, just add it to your project and use it like this:
```
NominatimReverseGeocodingJAPI nominatim1 = new NominatimReverseGeocodingJAPI(); //create instance with default zoom level (18)
NominatimReverseGeocodingJAPI nominatim2 = new NominatimReverseGeocodingJAPI(zoom); //create instance with given zoom level
nominatim1.getAdress(lat, lon); //returns Address object for the given position
```

An Address object has the following get-methods:
- getOsmId
- getOsmType
- getLod
- getCountryCode
- getCountry
- getPostcode
- getState
- getCounty
- getCity
- getSuburb
- getRoad
- getDisplayName

You can change the used Nominatim server by changing the NominatimInstance constant. For more information about Nominatim, usage policy, other servers and how to host your own server, visit the official Nominatim page.

More information: http://www.daniel-braun.com/technik/reverse-geocoding-library-for-java/