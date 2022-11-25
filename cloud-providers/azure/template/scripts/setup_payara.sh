#!/bin/bash

#
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
#
# Copyright (c) 2022 Payara Foundation and/or its affiliates. All rights reserved.
#
# The contents of this file are subject to the terms of either the GNU
# General Public License Version 2 only ("GPL") or the Common Development
# and Distribution License("CDDL") (collectively, the "License").  You
# may not use this file except in compliance with the License.  You can
# obtain a copy of the License at
# https://github.com/payara/Payara/blob/master/LICENSE.txt
# See the License for the specific
# language governing permissions and limitations under the License.
#
# When distributing the software, include this License Header Notice in each
# file and include the License file at glassfish/legal/LICENSE.txt.
#
# GPL Classpath Exception:
# The Payara Foundation designates this particular file as subject to the "Classpath"
# exception as provided by the Payara Foundation in the GPL Version 2 section of the License
# file that accompanied this code.
#
# Modifications:
# If applicable, add the following below the License Header, with the fields
# enclosed by brackets [] replaced by your own identifying information:
# "Portions Copyright [year] [name of copyright owner]"
#
# Contributor(s):
# If you wish your version of this file to be governed by only the CDDL or
# only the GPL Version 2, indicate your decision by adding "[Contributor]
# elects to include this software in this distribution under the [CDDL or GPL
# Version 2] license."  If you don't indicate a single choice of license, a
# recipient has the option to distribute your version of this file under
# either the CDDL, the GPL Version 2 or to extend the choice of license to
# its licensees as provided above.  However, if you add GPL Version 2 code
# and therefore, elected the GPL Version 2 license, then the option applies
# only if the new code is made subject to such option by the copyright
# holder.
#

set -ex

PAYARA_ADMIN="payaraadmin"
HOME_DIR="/home/$PAYARA_ADMIN"
PAYARA_ADMIN_ID_RSA_PATH="$HOME_DIR/.ssh/payara_id_rsa"

function install_packages
{
    apt -y update
    apt -y upgrade
    apt -y install openjdk-8-jdk-headless unzip
}

function download_payara
{
    cd $HOME_DIR
    rm -rf payara6
    wget https://repo.maven.apache.org/maven2/fish/payara/distributions/payara/6.2022.1/payara-6.2022.1.zip
    sudo -u $PAYARA_ADMIN unzip payara-6.2022.1.zip
}

function install_ssh_private_key
{
    local ssh_priv_key_b64=${1}

    echo $ssh_priv_key_b64 | base64 -d > $PAYARA_ADMIN_ID_RSA_PATH
    chown ${PAYARA_ADMIN}:${PAYARA_ADMIN} $PAYARA_ADMIN_ID_RSA_PATH
    chmod 400 $PAYARA_ADMIN_ID_RSA_PATH
}

function setup_payara_admin_server
{
    local admin_password=${1}

    cd $HOME_DIR/payara6/bin
    sudo -u $PAYARA_ADMIN ./asadmin start-domain production
    cat <<EOF > .passwordfile
AS_ADMIN_PASSWORD=
AS_ADMIN_NEWPASSWORD=$admin_password
EOF
    sudo -u $PAYARA_ADMIN ./asadmin --user admin --passwordfile .passwordfile change-admin-password

    echo "AS_ADMIN_PASSWORD=$admin_password" > .passwordfile

    sudo -u $PAYARA_ADMIN ./asadmin --user admin --passwordfile .passwordfile enable-secure-admin
    sudo -u $PAYARA_ADMIN ./asadmin stop-domain production
    sudo -u $PAYARA_ADMIN ./asadmin start-domain production

    rm -f .passwordfile

    # Install payara_production service in /etc/init.d so that Payara server starts up on VM reboots
    sudo ./asadmin create-service production
}

function setup_payara_servers
{
    local admin_password=${1}
    local node_name_prefix=${2}
    local node_count=${3}

    cd $HOME_DIR/payara6/bin

    echo "AS_ADMIN_PASSWORD=$admin_password" > .passwordfile

    for (( i = 0; i < $node_count; i++ )); do
        local node_name="${node_name_prefix}${i}"
        local server_name="PayaraServer${i}"

        sudo -u $PAYARA_ADMIN ./asadmin --user admin --passwordfile .passwordfile create-node-ssh --nodehost $node_name --installdir $HOME_DIR/payara6 --nodedir $HOME_DIR/payara6/glassfish/nodes --sshuser $PAYARA_ADMIN --sshkeyfile $PAYARA_ADMIN_ID_RSA_PATH --install true $server_name
    done

    sudo -u $PAYARA_ADMIN ./asadmin --user admin --passwordfile .passwordfile copy-config default-config cluster-config

    for (( i = 0; i < $node_count; i++ )); do
        local server_name="PayaraServer${i}"
        local instance_name="PS${i}-I1"

        sudo -u $PAYARA_ADMIN ./asadmin --user admin --passwordfile .passwordfile create-instance --node $server_name --config cluster-config $instance_name
    done

    sudo -u $PAYARA_ADMIN ./asadmin --user admin --passwordfile .passwordfile create-deployment-group payara-dg

    for (( i = 0; i < $node_count; i++ )); do
        local instance_name="PS${i}-I1"

        sudo -u $PAYARA_ADMIN ./asadmin --user admin --passwordfile .passwordfile add-instance-to-deployment-group --instance $instance_name --deploymentgroup payara-dg
    done

    sudo -u $PAYARA_ADMIN ./asadmin --user admin --passwordfile .passwordfile start-deployment-group payara-dg

    rm .passwordfile
}

function setup_payara_service_on_instance
{
    cd $HOME_DIR/payara6/bin

    sudo ./asadmin create-service --nodedir $HOME_DIR/payara6/glassfish/nodes
}

config_type=${1}
server_name_prefix=${2}
server_count=${3}
payara_admin_password=${4}
ssh_priv_key_b64=${5}

if [ "$config_type" != "install-instance-service" ]; then
    install_packages    # Should be done on both controller and server
fi

if [ "$config_type" = "controller" ]; then
    download_payara
    install_ssh_private_key $ssh_priv_key_b64
    setup_payara_admin_server $payara_admin_password
    setup_payara_servers $payara_admin_password $server_name_prefix $server_count
elif [ "$config_type" = "install-instance-service" ] ; then
    setup_payara_service_on_instance
fi
