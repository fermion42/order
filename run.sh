#!/bin/sh
echo "skywalking_agent_switch  : $SKYWALKING_AGENT_SWITCH"

if [ -n "$SKYWALKING_AGENT_SWITCH" ]; then
    sh -c "java -jar -javaagent:skywalking-agent/skywalking-agent.jar -Dspring.profiles.active=production app.jar"
else
    sh -c "java -jar -Dspring.profiles.active=production app.jar"
fi
