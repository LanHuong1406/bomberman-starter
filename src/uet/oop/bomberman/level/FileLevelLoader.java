package uet.oop.bomberman.level;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

import java.io.*;
import java.util.StringTokenizer;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đọc được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) {
		// TODO: đọc dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đọc được vào _width, _height, _level, _map
        try {
            InputStream stream = new FileInputStream("res/levels/Level" + level + ".txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String s = in.readLine();
            StringTokenizer tok = new StringTokenizer(s);
            _level = Integer.valueOf(tok.nextToken());
            _height = Integer.valueOf(tok.nextToken());
            _width = Integer.valueOf(tok.nextToken());

            _map = new char[_height][_width];

            for(int i=0;i<_height;i++)
            {
                String data = in.readLine();
                char [] line = data.toCharArray();
                for(int j = 0;j<_width;j++){
                    _map[i][j] = line[j];
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR!!!");
        }

    }

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình

        for (int _x = 0 ; _x < _width ; _x++ ){
            for (int _y = 0 ; _y < _height ; _y++ ){
                int pos = _x + _y * getWidth();
                char m = _map[_y][_x];

                switch (m){
                    //them wall
                    case '#' :
                        int xw = _x, yw = _y;
                        _board.addEntity(pos, new Wall(xw , yw , Sprite.wall));
                        break;

                    //them bomb Item
                    case 'b' :

                        int xbt = _x, ybt = _y;
                        _board.addEntity(pos, new LayeredEntity(xbt, ybt ,
                                        new Grass(xbt, ybt, Sprite.grass),
                                        new BombItem(xbt, ybt, Sprite.powerup_bombs),
                                        new Brick(xbt, ybt, Sprite.brick)
                                )
                        );
                        break;

                    //them bomber
                    case 'p' :
                        int xb = _x, yb = _y;
                        _board.addCharacter(new Bomber(Coordinates.tileToPixel(xb),
                                Coordinates.tileToPixel(yb) + Game.TILES_SIZE, _board));
                        Screen.setOffset(0,0);
                        _board.addEntity(pos, new Grass(xb, yb, Sprite.grass));
                        break;

                    //them Brick
                    case  '*' :
                        int xBrick = _x, yBrick = _y;
                        _board.addEntity(pos, new LayeredEntity(xBrick, yBrick,
                                new Grass(xBrick, yBrick, Sprite.grass),
                                new Brick(xBrick, yBrick, Sprite.brick)));
                        break;

                    //them Portal
                    case 'x' :
                        int xp = _x, yp = _y;
                        _board.addEntity(pos, new LayeredEntity(xp, yp,
                                new Grass(xp, yp, Sprite.grass),
                                new Portal(xp, yp, Sprite.portal, _board),
                                new Brick(xp, yp, Sprite.brick)));
                        break;

                    //them Grass: co nen
                    case ' ' :
                        int xG = _x, yG = _y;
                        _board.addEntity(pos, new Grass(xG, yG, Sprite.grass));
                        break;

                    //them flame Item: Vat pham them suc cong pha cua bom
                    case 'f' :
                        int xf = _x, yf = _y;
                        _board.addEntity(pos, new LayeredEntity(xf, yf,
                                new Grass(xf, yf, Sprite.grass),
                                new FlameItem(xf, yf, Sprite.powerup_flames),
                                new Brick(xf, yf, Sprite.brick)));
                        break;

                    //them Speed Item: Vat pham tang toc do nguoi choi
                    case 's' :
                        int xs = _x, ys =_y;
                        _board.addEntity(pos, new LayeredEntity(xs, ys,
                                new Grass(xs, ys, Sprite.grass),
                                new SpeedItem(xs, ys, Sprite.powerup_speed),
                                new Brick(xs, ys, Sprite.brick)));
                        break;

                    //them Ballom
                    case '1' :
                        int xbal = _x, ybal = _y;
                        _board.addCharacter(new Balloon(Coordinates.tileToPixel(xbal),
                                Coordinates.tileToPixel(ybal) + Game.TILES_SIZE, _board));
                        _board.addEntity(pos, new Grass(xbal, ybal, Sprite.grass));

                        break;

                    //them Oneal
                    case '2' :
                        int xo = _x, yo = _y;
                        _board.addCharacter(new Oneal(Coordinates.tileToPixel(xo),
                                Coordinates.tileToPixel(yo) + Game.TILES_SIZE, _board));
                        _board.addEntity(pos, new Grass(xo, yo, Sprite.grass));
                        break;

                    default:
                        _board.addEntity(pos, new Grass(_x, _y, Sprite.grass));
                        break;

                }
            }
        }

		// thêm Wall
//		for (int x = 0; x < 20; x++) {
//			for (int y = 0; y < 20; y++) {
//				int pos = x + y * _width;
//				Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
//				_board.addEntity(pos, new Grass(x, y, sprite));
//			}
//		}
//
//		// thêm Bomber
//		int xBomber = 1, yBomber = 1;
//		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
//		Screen.setOffset(0, 0);
//		_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
//
//		// thêm Enemy
//		int xE = 2, yE = 1;
//		_board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
//		_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
//
//		// thêm Brick
//		int xB = 3, yB = 1;
//		_board.addEntity(xB + yB * _width,
//				new LayeredEntity(xB, yB,
//					new Grass(xB, yB, Sprite.grass),
//					new Brick(xB, yB, Sprite.brick)
//				)
//		);
//
//		// thêm Item kèm Brick che phủ ở trên
//		int xI = 1, yI = 2;
//		_board.addEntity(xI + yI * _width,
//				new LayeredEntity(xI, yI,
//					new Grass(xI ,yI, Sprite.grass),
//					new SpeedItem(xI, yI, Sprite.powerup_flames),
//					new Brick(xI, yI, Sprite.brick)
//				)
//		);
	}

}
