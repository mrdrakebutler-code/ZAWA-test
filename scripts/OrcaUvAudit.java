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
            // Validate every cuboid's six-face UV footprint, not just the PNG size.
            Pattern cuboid = Pattern.compile("texOffs\\\\((\\\\d+),\\\\s*(\\\\d+)\\\\)\\\\.addBox\\\\(([^)]*)\\\\)");
            Matcher cubes = cuboid.matcher(source);
            int cuboidCount = 0;
            while (cubes.find()) {
                cuboidCount++;
                String[] coordinates = cubes.group(3).split(",");
                if (coordinates.length < 6) continue;
                try {
                    int u = Integer.parseInt(cubes.group(1)), v = Integer.parseInt(cubes.group(2));
                    int dx = (int)Math.ceil(Math.abs(Float.parseFloat(coordinates[3].trim().replace("F", ""))));
                    int dy = (int)Math.ceil(Math.abs(Float.parseFloat(coordinates[4].trim().replace("F", ""))));
                    int dz = (int)Math.ceil(Math.abs(Float.parseFloat(coordinates[5].trim().replace("F", ""))));
                    // Vanilla cube layout: UV width = 2*(X+Z), height = Y+Z.
                    if (u < 0 || v < 0 || u + 2 * (dx + dz) > width || v + dy + dz > height) {
                        System.out.printf("  UV OUT OF BOUNDS cuboid #%d: origin (%d,%d), size (%d,%d,%d), footprint (%d,%d)%n",
                            cuboidCount, u, v, dx, dy, dz, 2 * (dx + dz), dy + dz);
                        mismatches++;
                    }
                } catch (NumberFormatException ignored) {
                    System.out.println("  Skipped non-numeric cuboid #" + cuboidCount);
                }
            }
            System.out.println("  Cuboids checked: " + cuboidCount);
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
