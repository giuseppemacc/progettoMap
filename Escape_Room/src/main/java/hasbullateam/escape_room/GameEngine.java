
package hasbullateam.escape_room;


import hasbullateam.escape_room.escape_room_game.EscapeRoom;
import hasbullateam.escape_room.escape_room_game.EscapeRoomGame;
import java.util.function.Consumer;
import javax.swing.JPanel;

/**
 *
 * @author giuse
 */
public class GameEngine{
    
    public GameEngine( Consumer<JPanel> setPanel ){
        
        Menu menu = new Menu();
        
        menu.setGotoEscapeRoom( () -> { setPanel.accept(new EscapeRoomGame()); }  );
        menu.setGotoBattleShip( () -> { setPanel.accept(new BattleShip()); }  );
        menu.setGotoTris(       () -> { setPanel.accept(new Tris()); }        );
        menu.setGotoPingPong(   () -> { setPanel.accept(new PingPong()); }    );
        
        
        setPanel.accept( menu );
        
    }
    

}
