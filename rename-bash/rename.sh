case $command in
  x)
    # Extract
    for filePath in $a; do
      if [[ -f $filePath ]]; then
        extension=".${filePath##*.}"
        extension=$(echo $extension | tr '[:upper:]' '[:lower:]')

        if [[ $extension == '.cbr' || $extension == '.cbz' ]]; then
          aunpack "$filePath"
        fi
      fi
    done
    ;;

  z)
    # Zip compress
    for filePath in $a; do
      if [[ -d $filePath ]]; then
        7z a "$filePath.zip" "$filePath"
      fi
    done
    ;;

esac