package Figures;

public class Rook extends Figure{
    public Rook(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure fields[][]) {
        if (row == row1 || col == col1){
            // Проверка на препятствия, ходит по прямым
            if (Math.abs(row - row1) != 0) {
                int direction = row < row1 ? 1 : -1;
                for (int i = row + direction; Math.abs(row1 - i) > 0; i = i + direction) {
                    if (fields[i][col] != null) return false;
                }
            }
            if (Math.abs(col - col1) != 0) {
                int direction = col < col1 ? 1 : -1;
                for (int j = col + direction; Math.abs(col1 - j) > 0; j = j + direction) {
                    if (fields[row][j] != null) return false;
                }
            }

            return true;
        }

        return false;
    }
}
