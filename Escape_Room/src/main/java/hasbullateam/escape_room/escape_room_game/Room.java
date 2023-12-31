
package hasbullateam.escape_room.escape_room_game;

import hasbullateam.escape_room.type.Cord;
import hasbullateam.escape_room.type.Direction;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author giuse
 */
public class Room implements Serializable{
    String name;
    String backGroundPath;
    Map<Cord,ObjectSquare> objects;
    Cord playerPosition;
    
    String playerPathImageUP_0;
    String playerPathImageDOWN_0;
    String playerPathImageLEFT_0;
    String playerPathImageRIGHT_0;
    
    String playerPathImageUP_1;
    String playerPathImageDOWN_1;
    String playerPathImageLEFT_1;
    String playerPathImageRIGHT_1;
    
    String playerPathImageUP_2;
    String playerPathImageDOWN_2;
    String playerPathImageLEFT_2;
    String playerPathImageRIGHT_2;
    
    String previousRoomPath;
    String nextRoomPath;
    
    String previousRoomName;
    String nextRoomName;
    
    Direction playerDirection;

    public Room( String name, String backGroundPath, String playerPathImage, String previousRoomPath, String nextRoomPath, String previousRoomName, String nextRoomName ) {
        this(name, backGroundPath, new Cord(0,0), playerPathImage, previousRoomPath, nextRoomPath, previousRoomName, nextRoomName);
    }
    
    public Room(String name, String backGroundPath, Cord playerStartPosition, String playerPathImage, 
            String previousRoomPath, String nextRoomPath, String previousRoomName, String nextRoomName){
        this(name, backGroundPath, playerStartPosition,
                playerPathImage,playerPathImage,playerPathImage,playerPathImage,
                playerPathImage, playerPathImage, playerPathImage, playerPathImage,
                playerPathImage, playerPathImage, playerPathImage, playerPathImage, 
                Direction.UP, previousRoomPath, nextRoomPath, previousRoomName, nextRoomName);
    }
    
    public Room(String name, String backGroundPath, Cord playerStartPosition,
    String playerPathImageUP_0, String playerPathImageDOWN_0, String playerPathImageLEFT_0, String playerPathImageRIGHT_0, 
    String playerPathImageUP_1, String playerPathImageDOWN_1, String playerPathImageLEFT_1, String playerPathImageRIGHT_1,
    String playerPathImageUP_2, String playerPathImageDOWN_2, String playerPathImageLEFT_2, String playerPathImageRIGHT_2, 
    Direction playerStartDirection, String previosuRoomPath, String nextRoomPath, String previousRoomName, String nextRoomName){
        this.name = name;
        this.backGroundPath = backGroundPath;
        this.playerPosition = playerStartPosition;
        
        this.playerPathImageUP_0 = playerPathImageUP_0;
        this.playerPathImageDOWN_0 = playerPathImageDOWN_0;
        this.playerPathImageLEFT_0 = playerPathImageLEFT_0;
        this.playerPathImageRIGHT_0 = playerPathImageRIGHT_0;
        
        this.playerPathImageUP_1 = playerPathImageUP_1;
        this.playerPathImageDOWN_1 = playerPathImageDOWN_1;
        this.playerPathImageLEFT_1 = playerPathImageLEFT_1;
        this.playerPathImageRIGHT_1 = playerPathImageRIGHT_1;
        
        this.playerPathImageUP_2 = playerPathImageUP_2;
        this.playerPathImageDOWN_2 = playerPathImageDOWN_2;
        this.playerPathImageLEFT_2 = playerPathImageLEFT_2;
        this.playerPathImageRIGHT_2 = playerPathImageRIGHT_2;
        
        this.playerDirection = playerStartDirection;
        
        this.previousRoomPath = previosuRoomPath;
        this.nextRoomPath = nextRoomPath;
        this.previousRoomName = previousRoomName;
        this.nextRoomName = nextRoomName;
        
        objects = new HashMap<>();
    }
    
    public Room( JSONObject jsonObj ){
        
        this(   jsonObj.getString("name"),
                jsonObj.getString("backGroundPath"),
                new Cord(jsonObj.getJSONObject("playerStartPosition").getInt("x"),
                jsonObj.getJSONObject("playerStartPosition").getInt("y")),
                jsonObj.getString("playerPathImageUP_0"),
                jsonObj.getString("playerPathImageDOWN_0"),
                jsonObj.getString("playerPathImageLEFT_0"),
                jsonObj.getString("playerPathImageRIGHT_0"),
                jsonObj.getString("playerPathImageUP_1"),
                jsonObj.getString("playerPathImageDOWN_1"),
                jsonObj.getString("playerPathImageLEFT_1"),
                jsonObj.getString("playerPathImageRIGHT_1"),
                jsonObj.getString("playerPathImageUP_2"),
                jsonObj.getString("playerPathImageDOWN_2"),
                jsonObj.getString("playerPathImageLEFT_2"),
                jsonObj.getString("playerPathImageRIGHT_2"),
                Direction.fromString( jsonObj.getString("playerStartDirection")),
                jsonObj.getString("previous_room_path"),
                jsonObj.getString("next_room_path"),
                jsonObj.getString("previous_room_name"),
                jsonObj.getString("next_room_name")
        );
        
        JSONArray objArray = jsonObj.getJSONArray("objects");
        JSONObject currentObj;
        for(int i=0; i<objArray.length(); i++){
            
            currentObj = objArray.getJSONObject(i);
            
            if( currentObj.getString("tag").equals("container") ){
                this.addObject(new ContainerObjectSquare(currentObj));
            
            }else if (currentObj.getString("tag").equals("basic")){
               this.addObject( new ObjectSquare(currentObj) ); 
            
            }else if (currentObj.getString("tag").equals("door")){
                this.addObject( new DoorObjectSquare(currentObj) );
            
            }else if (currentObj.getString("tag").equals("collectable")){
                this.addObject( new CollectableObjectSquare(currentObj) );
            
            }else if (currentObj.getString("tag").equals("boss")){
                this.addObject( new BossObjectSquare(currentObj));
            
            }else if (currentObj.getString("tag").equals("broken_door")){
                this.addObject( new BrokenDoorObjectSquare(currentObj));
            
            }else if (currentObj.getString("tag").equals("breakable")){
                this.addObject( new BreakableObjectSquare(currentObj));
            
            }else if (currentObj.getString("tag").equals("keypad")){
                this.addObject( new NumericKeypadObjectSquare(currentObj));
            
            }
            
            
            
        }
            
        
    }
    
    
    public void addObject(ObjectSquare obj){
        objects.put(obj.position, obj);
    }
    
    public ObjectSquare getObject(Cord cord){
        return this.objects.get(cord);
    }
    
    public boolean containsObject(Cord cord){
        return this.objects.containsKey(cord);
    }
    
    public void removeObject(Cord cord){
        this.objects.remove(cord);
    }
    
    public ObjectSquare getFacingObject(){
        Cord cord = getFacingCord();
        
        return this.objects.get(cord);
    }
    
    public Cord getFacingCord(){
        Cord cord = playerPosition.clone();
        if(playerDirection == Direction.UP){
           cord.y--; 
        }else if(playerDirection == Direction.DOWN){
           cord.y++; 
        }else if(playerDirection == Direction.LEFT){
           cord.x--; 
        }else if(playerDirection == Direction.RIGHT){
           cord.x++; 
        }
        return cord;
    }
    
 
}
