## Rename

Chương trình Java để đổi lại tên file hàng loạt.

### Câu lệnh

```shell
# java Rename <command> <folder> [option]
rename <command> <folder> [option]
```

Trong đó `command` có thể có các giá trị sau:

| command | Mô tả                                             |
|---------|---------------------------------------------------|
| `pr`    | Prefix                                            |
| `pt`    | Postfix                                           |
| `lc`    | Lower case                                        |
| `lt`    | Left trim                                         |
| `rt`    | Right trim                                        |
| `et`    | Change extension                                  |
| `rc`    | Remove special characters (`:`, `!`, `_`, or any) |
| `csc`   | Check special characters (`:`, `!`)               |
| `sf`    | Sort file                                         |
| `z`     | Compress file                                     |
| `x`     | Giải nén các file                                 |
| `mc`    | Manga chapter                                     |

### Phiên bản chương trình sử dụng Bash

Sử dụng atool để extract, tự động kiểm tra xem file archive chỉ có chứa một file hoặc một thư mục ở gốc hay không. Nếu
không thì tạo một thư mục để chứa tất cả.

Cài đặt atool:

```shell
sudo apt-get install atool
```

Có các lệnh aunpack,...

aunpack = atool -x

acat = atool -c

als = atool -l

apack = atools -a

adiff = atool -d

aunpack from atool does this by default.

Usage: aunpack <archive file>

Available from most linux distro repos.

Khi giải nén file rar có thể bị lỗi:

```shell
aunpack: captain atom 05 (1987).cbr: format not known, identifying using file
aunpack: captain atom 05 (1987).cbr: format is `rar'
Can't exec "rar": No such file or directory at /usr/bin/aunpack line 1868.
rar: cannot execute - No such file or directory
aunpack: rar ...: non-zero return-code
```

Cần cài thêm:

```shell
sudo apt-get install rar
```

[Trang chủ atool](https://www.nongnu.org/atool/)

Ở môi trường KDE, sử dụng ark -ba <path>.

-b is short for --batch, and just signifies that this will be handled without using the GUI. Note, that the KDE
notification area will still be used for progress display.

-a is short for --autosubfolder, which creates a directory as mentioned above.

-a, --autosubfolder: Archive contents will be read, and if detected to not be a single folder or a single file archive,
a subfolder with the name of the archive will be created.

Have a look at ark --help for more things that can be done with the batch interface.

ark --help

Ở môi trường GNOME / Unity, sử dụng file-roller -h <path>.

Extract tùy định dạng:

```bash
#!/bin/bash
if [[ -f "$1" ]] ; then
    case $1 in
        *.tar.bz2) tar xjvf $1 ;;
        *.tar.gz) tar xzvf $1 ;;
        *.bz2) bunzip2 $1 ;;
        *.rar) rar x $1 ;;
        *.gz) gunzip $1 ;;
        *.tar) tar xf $1 ;;
        *.tbz2) tar xjvf $1 ;;
        *.tgz) tar xzvf $1 ;;
        *.zip) unzip $1 ;;
        *.Z) uncompress $1 ;;
        *.7z) 7z x $1 ;;
        *) echo "'$1' cannot be extracted with this utility" ;;
    esac
else
    echo "path '$1' does not exist or is not a file"
fi
```

