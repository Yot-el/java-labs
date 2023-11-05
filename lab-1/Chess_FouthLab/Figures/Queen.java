package Figures;

public class Queen extends Figure{
    public Queen(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure fields[][]) {
        if((row == row1 && col !=col1) ||(row != row1 && col ==col1) || (Math.abs(row - row1) == Math.abs(col-col1)) ){
            // Проверка на препятствия
            int row_direction = row == row1 ? 0 : (row < row1 ? 1 : -1);
            int col_direction = col == col1 ? 0 : (col < col1 ? 1 : -1);
            int i = row + row_direction;
            int j = col + col_direction;

            while (Math.abs(row1 - i) > 0 || Math.abs(col1 - j) > 0) {
                if (fields[i][j] != null) return false;
                i += row_direction;
                j += col_direction;
            }

            return true;
        }

        return false;
    }
}
