<?php

    if ($command == 'pt') {
        $rename->postfix($a, $argv[3]);
    } elseif ($command == 'z') {
        $rename->compress($a);
    } elseif ($command == 'csc') {
        $rename->checkSpecialCharacters($rootFolder);
    } elseif ($command == 'x') {
        $rename->extractFiles($a);
    }
