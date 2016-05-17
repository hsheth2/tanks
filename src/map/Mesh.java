package map;

import java.io.BufferedReader;
import java.io.FileReader;

public class Mesh {
	private Triangle[] triangles;
	
	public Mesh(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			int n = Integer.parseInt(br.readLine());
			
			triangles = new Triangle[n];
			
			for (int i = 0; i < n; i++) {
				triangles[i] = Triangle.parse(br.readLine());
			}
		} catch (Exception e) {
			
		}
	}
}
