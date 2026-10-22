import javax.imageio.ImageIO;
import java.nio.file.*;
import java.util.regex.*;
public class OrcaUvAudit {
    public static void main(String[] args) throws Exception {
        Path root = Path.of("src/main/resources/assets/zawa/textures/entity/orca");
        Path models = Path.of("src/main/java/org/zawamod/zawa/client/model/orca");
        int mismatches = 0;
        for (String name : new String[]{"OrcaAdultMaleLayer.java","OrcaAdultFemaleLayer.java","OrcaChildLayer.java"}) {
            String source = Files.readString(models.resolve(name));
            Matcher m = Pattern.compile("LayerDefinition\\.create\\(mesh,\\s*(\\d+),\\s*(\\d+)\\)").matcher(source);
            if (!m.find()) { System.out.println("Missing atlas declaration: " + name); mismatches++; continue; }
            int width = Integer.parseInt(m.group(1)), height = Integer.parseInt(m.group(2));
            System.out.printf("%s expects %dx%d%n", name, width, height);
            try (var paths = Files.list(root)) {
                for (Path png : paths.filter(p -> p.toString().endsWith(".png")).toList()) {
                    String file = png.getFileName().toString();
                    boolean applicable = name.contains("Male") ? file.matches("orca_\\d+_male\\.png")
                        : name.contains("Female") ? file.matches("orca_\\d+_female\\.png")
                        : file.equals("orca_baby.png");
                    if (!applicable) continue;
                    var image = ImageIO.read(png.toFile());
                    if (image == null) { System.out.println("Unreadable: " + file); mismatches++; continue; }
                    System.out.printf("  %s = %dx%d%s%n", file, image.getWidth(), image.getHeight(),
                        image.getWidth() == width && image.getHeight() == height ? " OK" : " MISMATCH");
                    if (image.getWidth() != width || image.getHeight() != height) mismatches++;
                }
            }
        }
        System.out.println("Atlas dimension mismatches: " + mismatches);
        if (mismatches != 0) System.exit(1);
    }
}
