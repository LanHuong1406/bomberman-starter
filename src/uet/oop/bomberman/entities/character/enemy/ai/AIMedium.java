package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;
	
	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đường đi
		int h;
		if ( _bomber == null){
			return random.nextInt(4);
		}

		int v = random.nextInt(2);
		if (v == 1){
			if (_bomber.getYTile() < _e.getYTile()){
				h = 0;
			}else if (_bomber.getYTile() > _e.getYTile()){
				h = 2;
			}else h = -1;

			if (h != -1){
				return h;
			}else {
				if (_bomber.getXTile() < _e.getXTile()){
					return 3;
				}else if (_bomber.getXTile() > _e.getXTile()){
					return 1;
				}else return -1;
			}
		}else {
			if (_bomber.getXTile() < _e.getXTile()) h = 3;
			else if (_bomber.getXTile() > _e.getXTile()) h =1;
			else h = -1;

			if (h != -1) return h;
			else {
				if (_bomber.getYTile() < _e.getYTile()) return 0;
				else if (_bomber.getYTile() > _e.getYTile()) return 2;
				else return -1;
			}
		}
	}

}
