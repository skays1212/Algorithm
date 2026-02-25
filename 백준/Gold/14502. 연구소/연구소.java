import java.util.*;

public class Main {
    static int N, M;
    static int[][] map;
    static int[][] tempMap;
    static int maxSafetyArea = 0;
    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        N = sc.nextInt();
        M = sc.nextInt();
        map = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                map[i][j] = sc.nextInt();
            }
        }

        // 벽 3개를 세우는 모든 경우의 수 탐색 시작
        buildWalls(0);

        System.out.println(maxSafetyArea);
        sc.close();
    }

    // 1. 벽 3개를 세우는 함수 (DFS/조합)
    static void buildWalls(int count) {
        if (count == 3) {
            bfs(); // 벽 3개가 다 세워지면 바이러스 퍼뜨리기 시작
            return;
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (map[i][j] == 0) {
                    map[i][j] = 1; // 벽 세우기
                    buildWalls(count + 1);
                    map[i][j] = 0; // 원상 복구 (백트래킹)
                }
            }
        }
    }

    // 2. 바이러스가 퍼지는 시뮬레이션 (BFS)
    static void bfs() {
        Queue<int[]> queue = new LinkedList<>();
        tempMap = new int[N][M];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                tempMap[i][j] = map[i][j];
                if (tempMap[i][j] == 2) {
                    queue.add(new int[]{i, j});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                if (nx >= 0 && nx < N && ny >= 0 && ny < M) {
                    if (tempMap[nx][ny] == 0) {
                        tempMap[nx][ny] = 2; // 바이러스 전염
                        queue.add(new int[]{nx, ny});
                    }
                }
            }
        }

        // 3. 안전 영역 계산
        calculateSafetyArea(tempMap);
    }

    static void calculateSafetyArea(int[][] resultGrid) {
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (resultGrid[i][j] == 0) {
                    count++;
                }
            }
        }
        maxSafetyArea = Math.max(maxSafetyArea, count);
    }
}