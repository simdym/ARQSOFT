# ARQSOFT
Project in ARQSOFT at UPC



Merging:

# Start a new feature
git checkout -b new-feature main
# Edit some files
git add <file>
git commit -m "Start a feature"
# Edit some files
git add <file>
git commit -m "Finish a feature"
# Merge in the new-feature branch
git checkout main
git pull
git merge new-feature
git branch -d new-feature