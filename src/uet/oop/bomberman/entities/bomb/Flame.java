package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Screen;

public class Flame extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] _flameSegments = new FlameSegment[0];

	/**
	 *
	 * @param x hoành độ bắt đầu của Flame
	 * @param y tung độ bắt đầu của Flame
	 * @param direction là hướng của Flame
	 * @param radius độ dài cực đại của Flame
	 */
	public Flame(int x, int y, int direction, int radius, Board board) {
		xOrigin = x;
		yOrigin = y;
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_board = board;
		createFlameSegments();
	}

	/**
	 * Tạo các FlameSegment, mỗi segment ứng một đơn vị độ dài
	 */
	private void createFlameSegments() {
		/**
		 * tính toán độ dài Flame, tương ứng với số lượng segment
		 */
		_flameSegments = new FlameSegment[calculatePermitedDistance()];

		/**
		 * biến last dùng để đánh dấu cho segment cuối cùng
		 */
		boolean last;

		// TODO: tạo các segment dưới đây
		int xa = 0;
		int ya = 0;
		if (_direction == 0) ya = -1;
		if (_direction == 1) xa = 1;
		if (_direction == 2) ya = 1;
		if (_direction == 3) xa = -1;
		for (int i = 0; i < _flameSegments.length; i++) {
			int xf = (int) (_x + xa * (i + 1));
			int yf = (int) (_y + ya * (i + 1));
			if (i == _flameSegments.length - 1) {
				_flameSegments[i] = new FlameSegment(xf, yf, _direction, true);
			} else {
				_flameSegments[i] = new FlameSegment(xf, yf, _direction, false);
			}
		}
	}

	/**
	 * Tính toán độ dài của Flame, nếu gặp vật cản là Brick/Wall, độ dài sẽ bị cắt ngắn
	 * @return
	 */
	private int calculatePermitedDistance() {
		// TODO: thực hiện tính toán độ dài của Flame
		int xa = 0;
		int ya = 0;
		if (_direction == 0) ya = -1;
		if (_direction == 1) xa = 1;
		if (_direction == 2) ya = 1;
		if (_direction == 3) xa = -1;

		for (int i = 0; i < _radius; i++) {
			int xf = (int) (_x + xa * (i + 1));
			int yf = (int) (_y + ya * (i + 1));
//            Entity entity = _board.getEntityAt(xf, yf);
			if (xf == _x && yf == _y) continue;
			Entity entity = _board.getEntity(xf, yf, null);
//            System.out.println(entity);
			entity.collide(this);
			if (entity instanceof Wall) return i;
		}
		return _radius;

	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
		for (int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}
		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý va chạm với Bomber, Enemy. Chú ý đối tượng này có vị trí chính là vị trí của Bomb đã nổ
		if (e instanceof Bomber) ((Bomber) e).kill();
		if (e instanceof Enemy) ((Enemy) e).kill();
//        if (e instanceof Brick) ((Brick) e).destroy();
		return false;

	}
}
