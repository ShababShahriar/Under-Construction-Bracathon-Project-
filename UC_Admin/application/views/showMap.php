
    <br><br>
    <h2 style="text-align:center"><b>Show Problems in Map</b></h2>  

   <div class="row" class="col-md-4">
        <table class="table">
            <thead>
                <tr>
                    <td></td>
                    <td></td>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td></td>
                    <td><input type="checkbox" id="vehicle" value="1"> Broken Road<br></td>
                    <td><input type="checkbox" id="vehicle1" value="1"> Broken Road<br></td>
                    <td><input type="checkbox" id="vehicle2" value="1"> Broken Road<br></td>
                    <td><input type="checkbox" id="vehicle3" value="1"> Broken Road<br></td>
                    <td><input type="checkbox" id="vehicle4" value="1"> Broken Road<br></td>
                    <td><input type="checkbox" id="vehicle5" value="1"> Broken Road<br></td>



                </tr>
                
            </tbody>
        </table>
    </div>
    <div class="row">
        <div class="col-md-5"></div>
        <div class="col-md-5">
            <button class="btn btn-danger" type="submit" onclick="initMap()">Show On Map</button>
        </div>
        <div class="col-md-2"></div>
    </div>
    <br>
    <div id="map" ></div>
            
        
    
        

    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC4LGcxa0bb_eUxxLYVv1sO6NRZ6UbPGsc&callback=initMap">
    </script>
  </body>
</html>