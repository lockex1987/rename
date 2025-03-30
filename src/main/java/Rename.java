import java.io.File;
import java.util.Arrays;
import java.util.List;


// javac Rename.java
// native-image --static --libc=musl Rename
// Copy file chạy vào ~/.local/bin
public class Rename {

    public String rootDir;

    public File[] ar;

    public static void main(String[] args) {
        System.out.println("Rename Java");
        // System.out.println("tap 001 x".replaceAll("\\s0(\\d\\d)", " $1"));
        new Rename().process(args);
    }

    public void process(String[] args) {
        if (args.length < 2) {
            System.out.println("Enter at least the command and the root directory");
            return;
        }

        String command = args[0];
        rootDir = normalizeRootDir(args[1]);
        ar = new File(rootDir).listFiles();
        switch (command) {
            case "lc":
                lowerCase();
                break;
            case "pr":
                addPrefix(args[2]);
                break;
            case "lt":
                leftTrim(Integer.parseInt(args[2]));
                break;
            case "rt":
                rightTrim(Integer.parseInt(args[2]));
                break;
            case "fl":
                fixLength(Integer.parseInt(args[2]));
                break;
            case "sf":
                sortFiles();
                break;
            case "et":
                changeExtension(args[2], args[3]);
                break;
            case "rc":
                removeCharacter(args.length > 2 ? args[2] : "");
                break;
            case "mc":
                mangaChapter();
                break;
            case "x":
                // extract();
                break;
            case "z":
                // compress();
                break;
            default:
                System.out.println("Invalid command");
                break;
        }
    }

    // Chuẩn hóa đường dẫn thư mục, không có ký tự / ở cuối
    public String normalizeRootDir(String rootDir) {
        if (rootDir.endsWith("/")) {
            return rootDir.substring(0, rootDir.length() - 1);
        }
        return rootDir;
    }

    public void lowerCase() {
        for (File f : ar) {
            String oldName = f.getName();
            String newName = oldName.toLowerCase();
            if (!newName.equals(oldName)) {
                System.out.println(oldName);
                f.renameTo(new File(rootDir + "/" + newName));
            }
        }
    }

    public void addPrefix(String prefix) {
        for (File f : ar) {
            String oldName = f.getName();
            String newName = prefix + oldName;
            f.renameTo(new File(rootDir + "/" + newName));
        }
    }

    public void leftTrim(int num) {
        for (File f : ar) {
            String oldName = f.getName();
            String newName = oldName.substring(num);
            f.renameTo(new File(rootDir + "/" + newName));
        }
    }

    public void rightTrim(int num) {
        for (File f : ar) {
            String oldName = f.getName();
            String newName;
            if (f.isDirectory()) {
                newName = oldName.substring(0, oldName.length() - num);
            } else {
                String[] temp = extractExtensionAndFileName(oldName);
                String extension = temp[0];
                String fileName = temp[1];
                newName = fileName.substring(0, fileName.length() - num) + "." + extension;
            }

            System.out.println(oldName + " -> " + newName);
            f.renameTo(new File(rootDir + "/" + newName));
        }
    }

    public void fixLength(int num) {
        for (File f : ar) {
            String oldName = f.getName();
            String newName;
            if (f.isDirectory()) {
                newName = oldName.substring(0, num);
            } else {
                String[] temp = extractExtensionAndFileName(oldName);
                String extension = temp[0];
                String fileName = temp[1];
                newName = fileName.substring(0, num) + "." + extension;
            }

            System.out.println(oldName + " -> " + newName);
            f.renameTo(new File(rootDir + "/" + newName));
        }
    }

    public void sortFiles() {
        List<String> oldNameList = Arrays.stream(ar)
            .map(f -> f.getName())
            .sorted()
            .toList();
        int i = 0;
        for (String oldName : oldNameList) {
            i++;
            String[] temp = extractExtensionAndFileName(oldName);
            String extension = temp[0];
            String newName = String.format("%03d", i) + "." + extension;
            if (!newName.equals(oldName) && !oldNameList.contains(newName)) {
                System.out.println(oldName + " -> " + newName);
                new File(rootDir + "/" + oldName).renameTo(new File(rootDir + "/" + newName));
            }
        }
    }

    public void changeExtension(String oldExt, String newExt) {
        for (File f : ar) {
            String oldName = f.getName();
            if (!f.isDirectory()) {
                String[] temp = extractExtensionAndFileName(oldName);
                String extension = temp[0];
                String fileName = temp[1];
                if (extension.equals(oldExt)) {
                    String newName = fileName + "." + newExt;
                    System.out.println(fileName);
                    f.renameTo(new File(rootDir + "/" + newName));
                }
            }
        }
    }

    public void removeCharacter(String other) {
        List<String> phraseList = List.of(
            "!",
            " (digital-empire)",
            " (zone-empire)",
            " (son of ultron-empire)",
            " (mephisto-empire)",
            " (lynx-empire)",
            " (danke-empire)",
            " (mr norrell-empire)",
            " (kileko-empire)",
            " (dr & quinch-empire)",
            " (shan-empire)",
            " (danke-repack)",
            " (webrip)",
            " (the last kryptonian-dcp)",
            " (cinebook)",
            " (digital)",
            " (1r0n)",
            " [720p]",
            " [bluray]",
            " [yts.mx]",
            " (lucaz)"
        );
        if (other != null && !other.isEmpty()) {
            phraseList.add(other);
        }

        for (File f : ar) {
            String oldName = f.getName();
            String newName = oldName.toLowerCase();
            newName = newName.replaceAll("_", " ");
            newName = newName.replaceAll(":", " - ");
            for (String phrase : phraseList) {
                newName = newName.replaceAll(phrase, "");
            }

            // Thay các chỉ số 001, 002,... thành 01, 02,...
            newName = newName.replaceAll("\\s0(\\d\\d)", " $1");

            if (!newName.equals(oldName)) {
                System.out.println(newName);
                f.renameTo(new File(rootDir + "/" + newName));
            }
        }
    }

    public void mangaChapter() {
        for (File subFolder : ar) {
            if (subFolder.isDirectory()) {
                String folderName = subFolder.getName();
                File[] arInSubFolder = new File(rootDir + "/" + folderName).listFiles();
                List<String> nameList = Arrays.stream(arInSubFolder)
                    .map(f -> f.getName())
                    .sorted()
                    .toList();

                int idx = folderName.lastIndexOf(" ");
                String prefix = (idx > 0 ? folderName.substring(idx + 1) : folderName) + "-";

                int i = 0;
                for (String oldName : nameList) {
                    i++;
                    String[] temp = extractExtensionAndFileName(oldName);
                    String extension = temp[0];
                    String newName = prefix + String.format("%03d", i) + "." + extension;
                    System.out.println(oldName + " -> " + newName);
                    new File(rootDir + "/" + folderName + "/" + oldName).renameTo(new File(rootDir + "/" + newName));
                }
            }
        }
    }

    public String[] extractExtensionAndFileName(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos > 0 && pos < (fileName.length() - 1)) {
            // If '.' is not the first or last character
            // extension không chứa dấu chấm
            return new String[]{
                fileName.substring(pos + 1),
                fileName.substring(0, pos),
            };
        }
        return new String[]{
            "",
            fileName
        };
    }
}