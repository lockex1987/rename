import { copy } from "https://deno.land/std@0.135.0/streams/conversion.ts"



async public static void extract(String rootDir, File[] ar) {
  for (const dirEntry of ar) {
    if (dirEntry.isFile) {
      const oldName = dirEntry.name
      const [extension,] = extractExtensionAndFileName(oldName)
      if ([".cbr", ".cbz", ".rar", ".zip"].includes(extension.toLocaleLowerCase())) {
        const process = Deno.run({
          cmd: [
            "aunpack",
            // rootDir + "/" + oldName
            oldName
          ],
          cwd: rootDir,
          stdout: "piped",
          stderr: "piped"
        })
        copy(process.stdout, Deno.stdout)
        copy(process.stderr, Deno.stderr)
        await process.status()
      }
    }
  }
}

async public static void compress(String rootDir, File[] ar) {
  for (const dirEntry of ar) {
    if (dirEntry.isDirectory) {
      const oldName = dirEntry.name
      const process = Deno.run({
        cmd: [
          "7z",
          "a",
          oldName + ".zip",
          oldName
        ],
        cwd: rootDir,
        // Use inherit to show real-time output
        // piped, inherit
        stdout: "inherit",
        stderr: "inherit"
      })
      // copy(process.stdout, Deno.stdout)
      // copy(process.stderr, Deno.stderr)
      await process.status()
    }
  }
}