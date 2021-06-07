FROM boxfuse/flyway:latest
RUN apt-get update && \
    apt-get install dos2unix && \
    apt-get clean
RUN mkdir /flyway/bin

ADD wait-for-it.sh 	/flyway/bin/wait-for-it.sh
RUN dos2unix 		/flyway/bin/wait-for-it.sh
RUN chmod +x 		/flyway/bin/wait-for-it.sh

ADD wait-web.sh 	/flyway/bin/wait-web.sh
RUN dos2unix 		/flyway/bin/wait-web.sh
RUN chmod +x 		/flyway/bin/wait-web.sh

ADD ./sql  			/flyway/sql	
ADD ./conf	 		/flyway/conf	