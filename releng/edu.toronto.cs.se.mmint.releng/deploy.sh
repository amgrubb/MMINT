#! /bin/sh
ssh modelling@colony.cs.toronto.edu 'rm -rf ~/site/www/mmint/updates/'
scp -r releng/edu.toronto.cs.se.mmint.install/target/repository/ modelling@colony.cs.toronto.edu:~/site/www/mmint/updates/
