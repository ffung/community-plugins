def ensureStableServerState(server):
    for a in range(1, 5):
        status = cmo.lookupServerLifeCycleRuntime(server.name).getState()
        print "Server: ", server.name, ": ", status

        if (status not in ['SHUTTING_DOWN', 'STARTING']):
            return True

        print "# ", a, " Sleeping 2 seconds"
        java.lang.Thread.sleep(2000)

    return False

domainRuntime()

if ensureStableServerState(target):
    if exists('/ServerRuntimes/' + target.name):
        print 'Restarting SSL Channels for ' + target.name
        cd ('/ServerRuntimes/' + target.name)
        cmo.restartSSLChannels()
        print 'Restarted SSL Channels for ' + target.name
    else:
        print 'Restarting SSL Channels for ' + target.name + ' is not necessary, target is down'
else:
    print 'Waiting for a stable server state has timed out, the server ' + target.name + ' is still SHUTTING DOWN or STARTING'
    print 'Restarting SSL Channels failed'
    sys.exit(1)