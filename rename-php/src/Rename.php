<?php

class Rename
{

    /**
     * Thêm postfix phía sau.
     */
    public function postfix(array $a, string $postfix): void
    {
        foreach ($a as $f) {
            list($name, $ext) = $this->extractFileName($f);
            $newName = $name . $postfix . $ext;
            $this->rename($f, $newName);
        }
    }



    /**
     * Nén thư mục.
     */
    public function compress(array $a): void
    {
        natcasesort($a);

        $compressor = new SevenZipWrapper();

        chdir($this->rootFolder);

        foreach ($a as $f) {
            if (is_dir(realpath($f))) {
                $compressor->compressFolder($f, $f . '.zip');
            }
        }

        // chdir($currentFolder);
    }

    /**
     * Loại bỏ các ký tự đặc biệt, để có thể di chuyển file giữa Windows và Linux.
     */
    public function checkSpecialCharacters(string $folder): void
    {
        $a = scandir($folder);
        foreach ($a as $f) {
            if (!in_array($f, ['.', '..'])) {
                $absPath = $folder . '/' . $f;
                if (str_contains($f, ':') || str_contains($f, '!')) {
                    $newName = str_replace(':', ' - ', $f);
                    $newName = str_replace('!', '', $newName);
                    $newPath = $folder . '/' . $newName;
                    echo $absPath . ' -> ' . $newPath . PHP_EOL;
                    rename($absPath, $newPath);
                }
                if (is_dir(realpath($absPath))) {
                    $this->checkSpecialCharacters($absPath);
                }
            }
        }
    }

    public function extractFiles(array $a): void
    {
        $extractor = new SevenZipWrapper();
        foreach ($a as $f) {
            list($name, $ext) = $this->extractFileName($f);
            $extWithoutDot = substr($ext, 1);
            if (in_array($extWithoutDot, ['zip', 'rar', 'cbz', 'cbr'])) {
                $path = CommonUtils::joinPath($this->rootFolder, $f);

                // $extractor->extract7z($path, $this->rootFolder);
                $extractor->extractNative($path, $this->rootFolder);
            }
        }
    }
}
