FROM centos:7
MAINTAINER uryyyyyyy <koki305@gmail.com>
RUN yum update -y
RUN yum clean all
RUN yum install tcl gcc wget make vim net-tools -y
RUN wget http://www.memcached.org/files/memcached-1.4.25.tar.gz
RUN tar -zxvf memcached-1.4.25.tar.gz
RUN yum -y install libevent-devel

WORKDIR memcached-1.4.25
RUN ./configure --enable-64bit
RUN make
RUN make install

RUN useradd memcache
USER memcache

EXPOSE 11121
CMD [ "/bin/bash" ]
