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
		try{
			InputStream inputStream = new FileInputStream("res/levels/level" + level + ".txt");
			BufferedReader bw = new BufferedReader(new InputStreamReader(inputStream));

			String data = bw.readLine();
			StringTokenizer tokens = new StringTokenizer(data);

			_level = Integer.parseInt(tokens.nextToken());
			_height = Integer.parseInt(tokens.nextToken());
			_width = Integer.parseInt(tokens.nextToken());

			_map = new char[_height][_width];



			for (int i = 0; i < _height; i++) {
				String dataLine = bw.readLine();
				char[] charInLines = dataLine.toCharArray();
				for (int j = 0; j < _width; j++) {
					_map[i][j] = charInLines[j];
				}
			}

			bw.close();
		}catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của màn chơi
		// TODO: sau khi tạo xong, gọi _board.addEntity() để thêm Entity vào game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity vào game
		// TODO: hãy xóa nó khi hoàn thành chức năng load màn chơi từ tệp cấu hình
		// thêm Wall
		for (int x = 0; x < _width; x++) {
			for (int y = 0; y < _height; y++) {
				int pos = x + y * _width;
				// thêm Grass
				_board.addEntity(pos, new Grass(x, y, Sprite.grass));

				// thêm Wall
				if (_map[y][x] == '#') {
					_board.addEntity(pos, new Wall(x, y, Sprite.wall));
				}

				// thêm Bomber
				if (_map[y][x] == 'p') {
					int xBomber = x, yBomber = y;
					_board.addCharacter(new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board));
					Screen.setOffset(0, 0);
					_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));
				}

				// thêm Enemy
				if (_map[y][x] == '1') {
					int xE = x, yE = y;
					_board.addCharacter(new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
					_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
				}
				if (_map[y][x] == '2') {
					int xE = x, yE = y;
					_board.addCharacter(new Oneal(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
					_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));
				}


				// thêm Brick
				if (_map[y][x] == '*') {
					int xB = x, yB = y;
					_board.addEntity(xB + yB * _width,
							new LayeredEntity(xB, yB,
									new Grass(xB, yB, Sprite.grass),
									new Brick(xB, yB, Sprite.brick)
							)
					);
				}

				// thêm Item kèm Brick che phủ ở trên
				if (_map[y][x] == 'x') {
					int xI = x, yI = y;
					_board.addEntity(xI + yI * _width,
							new LayeredEntity(xI, yI,
									new Grass(xI, yI, Sprite.grass),
									new Portal(xI, yI, Sprite.portal),
									new Brick(xI, yI, Sprite.brick)
							)
					);
				}
				if (_map[y][x] == 'b') {
					int xI = x, yI = y;
					_board.addEntity(xI + yI * _width,
							new LayeredEntity(xI, yI,
									new Grass(xI, yI, Sprite.grass),
									new BombItem(xI, yI, Sprite.powerup_bombs),
									new Brick(xI, yI, Sprite.brick)
							)
					);
				}
				if (_map[y][x] == 'f') {
					int xI = x, yI = y;
					_board.addEntity(xI + yI * _width,
							new LayeredEntity(xI, yI,
									new Grass(xI, yI, Sprite.grass),
									new SpeedItem(xI, yI, Sprite.powerup_speed),
									new Brick(xI, yI, Sprite.brick)
							)
					);
				}
				if (_map[y][x] == 's') {
					int xI = x, yI = y;
					_board.addEntity(xI + yI * _width,
							new LayeredEntity(xI, yI,
									new Grass(xI, yI, Sprite.grass),
									new FlameItem(xI, yI, Sprite.powerup_flames),
									new Brick(xI, yI, Sprite.brick)
							)
					);
				}

			}
		}
	}

}
