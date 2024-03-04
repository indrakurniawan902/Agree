#!/bin/bash

FILE=.git/hooks/pre-commit
if [ -f "$FILE" ]; then
    echo "$FILE detected, Scaning file..."
    existingHooks=".git/hooks/pre-commit"
    newHooks="pre-commit"

    if cmp -s "$existingHooks" "$newHooks"; then
        printf 'The file "%s" is the same as "%s"\n' "$existingHooks" "$newHooks"
    else
        printf 'The file "%s" is different from "%s"\n' "$existingHooks" "$newHooks"
        rm -rf .git/hooks/pre-commit
        cp pre-commit .git/hooks/
        chmod -R 0777 .git/hooks/
    fi

else
    echo "$FILE does not exist. Installing new script"
    cp pre-commit .git/hooks/
    chmod -R 0777 .git/hooks/
fi

