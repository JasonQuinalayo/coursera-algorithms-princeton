import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;

public class BaseballElimination {
    private final int[] wins, losses, remaining;
    private final int[][] against;
    private final int numOfTeams;
    private final HashMap<String, Integer> teamNameToIndex = new HashMap<String, Integer>();
    private final String[] teams;
    private final HashMap<String, TeamEliminationStatus> cache
            = new HashMap<String, TeamEliminationStatus>();

    public BaseballElimination(String filename) {
        In input = new In(filename);
        numOfTeams = input.readInt();
        wins = new int[numOfTeams];
        losses = new int[numOfTeams];
        remaining = new int[numOfTeams];
        against = new int[numOfTeams][numOfTeams];
        teams = new String[numOfTeams];
        int teamIndex = 0;
        while (!(input.isEmpty())) {
            String teamName = input.readString();
            teams[teamIndex] = teamName;
            teamNameToIndex.put(teamName, teamIndex);
            wins[teamIndex] = input.readInt();
            losses[teamIndex] = input.readInt();
            remaining[teamIndex] = input.readInt();
            for (int i = 0; i < numOfTeams; i++) {
                against[teamIndex][i] = input.readInt();
            }
            teamIndex++;
        }
    }

    public int numberOfTeams() {
        return numOfTeams;
    }

    public Iterable<String> teams() {
        return teamNameToIndex.keySet();
    }

    public int wins(String team) {
        validateInputTeam(team);
        return wins[teamNameToIndex.get(team)];
    }

    public int losses(String team) {
        validateInputTeam(team);
        return losses[teamNameToIndex.get(team)];
    }

    public int remaining(String team) {
        validateInputTeam(team);
        return remaining[teamNameToIndex.get(team)];
    }

    public int against(String team1, String team2) {
        validateInputTeam(team1);
        validateInputTeam(team2);
        return against[teamNameToIndex.get(team1)][teamNameToIndex.get(team2)];
    }

    public boolean isEliminated(String team) {
        validateInputTeam(team);
        if (cache.containsKey(team)) {
            return cache.get(team).isEliminated;
        }
        EliminationComputation eliminationComputation = new EliminationComputation(team);
        boolean isEliminated = eliminationComputation.isEliminated;
        cache.put(team, new TeamEliminationStatus(isEliminated,
                                                  eliminationComputation.certificateOfElimination));
        return isEliminated;
    }

    public Iterable<String> certificateOfElimination(String team) {
        validateInputTeam(team);
        if (cache.containsKey(team)) {
            return cache.get(team).certificateOfElimination;
        }
        EliminationComputation eliminationComputation = new EliminationComputation(team);
        HashSet<String> certificateOfElimination = eliminationComputation.certificateOfElimination;
        cache.put(team, new TeamEliminationStatus(eliminationComputation.isEliminated,
                                                  certificateOfElimination));
        return certificateOfElimination;
    }

    private void validateInputTeam(String team) {
        if (!(teamNameToIndex.containsKey(team))) throw new IllegalArgumentException();
    }

    private class EliminationComputation {
        private final int teamIndex;
        private final int totalNumOfVertices = getNumOfPairings(numOfTeams - 1) + numOfTeams + 1;
        private FlowNetwork flowNetwork;
        private boolean isEliminated = false;
        private final int[] vertexToTeamIndex = new int[numOfTeams - 1];
        private final int sourceVertex = totalNumOfVertices - 2;
        private final int targetVertex = totalNumOfVertices - 1;
        private int sumOfRemainingGames = 0;
        private HashSet<String> certificateOfElimination = new HashSet<>();

        private EliminationComputation(String team) {
            teamIndex = teamNameToIndex.get(team);
            eliminateTrivially();
            if (isEliminated) return;
            constructFlowNetwork();
            FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, sourceVertex,
                                                            targetVertex);
            eliminateNonTrivially(fordFulkerson);
            if (!isEliminated) certificateOfElimination = null;
        }

        private void eliminateTrivially() {
            int maxScore = wins[teamIndex] + remaining[teamIndex];
            for (int i = 0; i < numOfTeams; i++) {
                if (i == teamIndex) continue;
                int maxPossibleGap = maxScore - wins[i];
                if (maxPossibleGap < 0) {
                    isEliminated = true;
                    certificateOfElimination.add(teams[i]);
                }
            }
        }

        private void constructFlowNetwork() {
            flowNetwork = new FlowNetwork(totalNumOfVertices);
            addTeamsToTargetEdges();
            addSourceToTeamsEdges();
        }

        private void addTeamsToTargetEdges() {
            int vertex = 0;
            int maxScore = wins[teamIndex] + remaining[teamIndex];
            for (int i = 0; i < numOfTeams; i++) {
                if (i == teamIndex) continue;
                int maxPossibleGap = maxScore - wins[i];
                vertexToTeamIndex[vertex] = i;
                FlowEdge flowEdge;
                flowEdge = new FlowEdge(vertex, targetVertex,
                                        maxPossibleGap);
                flowNetwork.addEdge(flowEdge);
                vertex++;
            }
        }

        private void addSourceToTeamsEdges() {
            int vertex = numOfTeams - 1;
            int firstVertex = 0;
            for (int i = 0; i < numOfTeams; i++) {
                if (i == teamIndex) continue;
                int secondVertex = firstVertex + 1;
                for (int j = i + 1; j < numOfTeams; j++) {
                    if (j == teamIndex) continue;
                    flowNetwork
                            .addEdge(new FlowEdge(sourceVertex, vertex, against[i][j]));
                    sumOfRemainingGames += against[i][j];
                    flowNetwork
                            .addEdge(new FlowEdge(vertex, firstVertex, Double.POSITIVE_INFINITY));
                    flowNetwork
                            .addEdge(new FlowEdge(vertex, secondVertex, Double.POSITIVE_INFINITY));
                    vertex++;
                    secondVertex++;
                }
                firstVertex++;
            }
        }

        private void eliminateNonTrivially(FordFulkerson fordFulkerson) {
            if (sumOfRemainingGames != fordFulkerson.value()) {
                isEliminated = true;
                for (int i = 0; i < numOfTeams - 1; i++) {
                    if (fordFulkerson.inCut(i)) {
                        certificateOfElimination.add(teams[vertexToTeamIndex[i]]);
                    }
                }
            }
        }
    }

    private class TeamEliminationStatus {
        private final boolean isEliminated;
        private final HashSet<String> certificateOfElimination;

        private TeamEliminationStatus(boolean isEliminated,
                                      HashSet<String> certificateOfElimination) {
            this.isEliminated = isEliminated;
            this.certificateOfElimination = certificateOfElimination;
        }
    }

    private static int getNumOfPairings(int n) {
        return n * (n + 1) / 2;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
