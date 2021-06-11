package utill;
//앞으로 파일과 관련된 여러 작업을 전담하게 될 파일 제어 Class
public class FileManager {
	//넘겨받은 경로를 통해 확장자만 추출하기..
	public static String getExtend(String path, String token) {
		int lastIndex = path.lastIndexOf(token); //넘겨받은 문자열의 가장 마지막 디렉토리구분자의 위치.
		String filename = path.substring(lastIndex + 1, path.length());
		String ext = filename.substring(filename.lastIndexOf(".")+1, filename.length());
		//System.out.println();
		return ext;
	};
}
